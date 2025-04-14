package com.example.abc_app.di.modules

import com.example.abc_app.domain.usecase.GetListItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideUseCase(): GetListItemsUseCase = GetListItemsUseCase()

}