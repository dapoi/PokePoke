package com.project.compose.core.data.repository.base

import com.project.compose.core.common.utils.state.ApiState.Error
import com.project.compose.core.common.utils.state.ApiState.Loading
import com.project.compose.core.common.utils.state.ApiState.Success
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

open class BaseRepository {
    inline fun <T, R> collectApiResult(
        crossinline fetchApi: suspend () -> T,
        crossinline transformData: (T) -> R
    ) = flow {
        emit(Loading)
        try {
            val response = fetchApi()
            val mappedData = transformData(response)
            emit(Success(mappedData))
        } catch (e: Throwable) {
            emit(Error(e))
        }
    }.catch { throwable ->
        emit(Error(throwable))
    }

    inline fun <T, reified LocalType> collectApiResult(
        crossinline fetchApi: suspend () -> T,
        crossinline transformData: (T) -> LocalType,
        crossinline saveToDb: suspend (LocalType) -> Unit,
        crossinline fetchFromDb: () -> Flow<LocalType>
    ) = flow {
        emit(Loading)

        try {
            val response = fetchApi()
            val entity = transformData(response)
            saveToDb(entity)
            emit(Success(entity))
        } catch (e: Throwable) {
            emitAll(
                fetchFromDb().map { cache ->
                    val isNotEmpty = when (cache) {
                        is Collection<*> -> cache.isNotEmpty()
                        null -> false
                        else -> true
                    }

                    if (isNotEmpty) Success(cache) else Error(e)
                }
            )
        }
    }.flowOn(IO)
}