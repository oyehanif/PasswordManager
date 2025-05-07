package com.hanif.passwordmanager.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room Database class for the password manager application
 */
@Database(
    entities = [PasswordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PasswordDatabase : RoomDatabase() {
    /**
     * Returns PasswordDao for accessing the passwords table
     */
    abstract fun passwordDao(): PasswordDao
}