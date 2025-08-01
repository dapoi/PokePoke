package com.project.compose.feature.splash.di

import com.project.compose.core.navigation.base.BaseNavGraph
import com.project.compose.feature.splash.navigation.SplashNavGraphImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SplashNavGraphModule {

    @Binds
    @IntoSet
    abstract fun bindSplashNavGraph(impl: SplashNavGraphImpl): BaseNavGraph
}