package com.hanif.passwordmanager.di

import android.content.Context
import androidx.room.Room
import com.hanif.passwordmanager.model.PasswordRepository
import com.hanif.passwordmanager.model.local.PasswordDao
import com.hanif.passwordmanager.model.local.PasswordDatabase
import com.hanif.passwordmanager.utils.EncryptionUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides Room database instance
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PasswordDatabase {
        return Room.databaseBuilder(
            context, PasswordDatabase::class.java, "password_database"
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Provides PasswordDao instance
     */
    @Provides
    @Singleton
    fun providePasswordDao(database: PasswordDatabase): PasswordDao {
        return database.passwordDao()
    }

    /**
     * Provides EncryptionManager instance
     */
    @Provides
    @Singleton
    fun provideEncryptionManager(): EncryptionUtils {
        return EncryptionUtils()
    }

    /**
     * Provides PasswordRepository instance
     */
    @Provides
    @Singleton
    fun providePasswordRepository(
        passwordDao: PasswordDao
    ): PasswordRepository {
        return PasswordRepository(passwordDao)
    }
}