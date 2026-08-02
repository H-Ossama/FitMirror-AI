package com.fitmirror.ai.di

import com.fitmirror.ai.data.repository.MockAuthRepositoryImpl
import com.fitmirror.ai.data.repository.MockHistoryRepositoryImpl
import com.fitmirror.ai.data.repository.MockSettingsRepositoryImpl
import com.fitmirror.ai.data.repository.MockSubscriptionRepositoryImpl
import com.fitmirror.ai.data.repository.MockTryOnRepositoryImpl
import com.fitmirror.ai.domain.repository.AuthRepository
import com.fitmirror.ai.domain.repository.HistoryRepository
import com.fitmirror.ai.domain.repository.SettingsRepository
import com.fitmirror.ai.domain.repository.SubscriptionRepository
import com.fitmirror.ai.domain.repository.TryOnRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        mockAuthRepositoryImpl: MockAuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTryOnRepository(
        mockTryOnRepositoryImpl: MockTryOnRepositoryImpl
    ): TryOnRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(
        mockHistoryRepositoryImpl: MockHistoryRepositoryImpl
    ): HistoryRepository

    @Binds
    @Singleton
    abstract fun bindSubscriptionRepository(
        mockSubscriptionRepositoryImpl: MockSubscriptionRepositoryImpl
    ): SubscriptionRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        mockSettingsRepositoryImpl: MockSettingsRepositoryImpl
    ): SettingsRepository
}
