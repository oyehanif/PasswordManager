package com.hanif.passwordmanager.model.local

import androidx.room.*

/**
 * Entity class for storing password information
 */
@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val serviceName: String,
    val username: String,
    val encryptedPassword: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)