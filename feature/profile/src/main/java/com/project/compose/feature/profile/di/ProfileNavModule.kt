package com.project.compose.feature.profile.di

import com.project.compose.core.navigation.base.BaseNavGraph
import com.project.compose.feature.profile.navigation.ProfileNavGraphImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileNavModule {

    @Binds
    @IntoSet
    abstract fun bindProfileNavGraph(navGraph: ProfileNavGraphImpl): BaseNavGraph
}