package com.hanif.passwordmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "credentials")
data class CredentialEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val serviceName: String,
    val username: String,
    val password: String,
    val maskedPassword: String = "••••••••"
)