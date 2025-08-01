package com.project.compose.feature.auth.di

import com.project.compose.core.navigation.base.BaseNavGraph
import com.project.compose.feature.auth.navigation.AuthNavGraphImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthNavGraphModule {

    @Binds
    @IntoSet
    abstract fun bindAuthNavGraph(impl: AuthNavGraphImpl): BaseNavGraph
}