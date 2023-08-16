package com.arcadone.cloudsharesnap.di

import com.arcadone.cloudsharesnap.networkconnection.NetworkConnection
import com.arcadone.cloudsharesnap.networkconnection.NetworkConnectionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkingModule {
    @Provides
    @Singleton
    fun provideNetworkConnection(netConn: NetworkConnectionImpl): NetworkConnection {
        return netConn
    }
}
