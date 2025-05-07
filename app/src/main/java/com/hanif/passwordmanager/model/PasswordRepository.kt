package com.hanif.passwordmanager.model

import android.util.Log
import com.hanif.passwordmanager.model.local.PasswordDao
import com.hanif.passwordmanager.model.local.PasswordEntity
import com.hanif.passwordmanager.utils.EncryptionUtils
import com.hanif.passwordmanager.utils.RSA_KEY_ALIAS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing password data
 * Acts as a single source of truth for password data in the application
 */
@Singleton
class PasswordRepository @Inject constructor(
    private val passwordDao: PasswordDao,
) {
    /**
     * Gets all passwords from the database and maps them to domain model
     */
    suspend fun getAllPasswords(): List<PasswordEntity?> = withContext(Dispatchers.IO) {
        return@withContext passwordDao.getAllPasswords().map { entity ->
            decryptPassword(entity.encryptedPassword)?.let {
                PasswordEntity(
                    id = entity.id,
                    serviceName = entity.serviceName,
                    username = entity.username,
                    encryptedPassword = it,
                )
            }
        }
    }

    /**
     * Saves a new password to the database
     */
    suspend fun savePassword(passwordItem: PasswordEntity) = withContext(Dispatchers.IO) {
        encryptPassword(passwordItem.encryptedPassword)?.let {
            val insert = PasswordEntity(
                id = passwordItem.id,
                serviceName = passwordItem.serviceName,
                username = passwordItem.username,
                encryptedPassword = it
            )
            passwordDao.insertPassword(insert)
        }
    }

    /**
     * Updates an existing password in the database
     */
    suspend fun updatePassword(passwordItem: PasswordEntity) = withContext(Dispatchers.IO) {
        encryptPassword(passwordItem.encryptedPassword)?.let {
            val update = PasswordEntity(
                id = passwordItem.id,
                serviceName = passwordItem.serviceName,
                username = passwordItem.username,
                encryptedPassword = it
            )
            passwordDao.updatePassword(update)
        }
    }

    /**
     * Deletes a password from the database by ID
     */
    suspend fun deletePassword(passwordId: Int) = withContext(Dispatchers.IO) {
        passwordDao.deletePasswordById(passwordId)
    }

    /**
     * Gets a specific password by ID
     */
    suspend fun getPasswordById(passwordId: String): PasswordEntity? = withContext(Dispatchers.IO) {
        val entity = passwordDao.getPasswordById(passwordId) ?: return@withContext null
        return@withContext decryptPassword(entity.encryptedPassword)?.let {
            PasswordEntity(
                id = entity.id,
                serviceName = entity.serviceName,
                username = entity.username,
                encryptedPassword = it,
            )
        }
    }

    /**
     * Encrypts a password using the encryption manager
     */
    private suspend fun encryptPassword(password: String): String? = withContext(Dispatchers.IO) {
        try {
            EncryptionUtils.RSA.encrypt(password, RSA_KEY_ALIAS)
        } catch (e: Exception) {
            Log.e("TAG", "Error securing master password", e)
            null
        }
    }

    /**
     * Decrypts a password using the encryption manager
     */
    private suspend fun decryptPassword(encryptedPassword: String): String? = withContext(Dispatchers.IO) {
        try {
            EncryptionUtils.RSA.decrypt(encryptedPassword, RSA_KEY_ALIAS)
        } catch (e: Exception) {
            Log.e("TAG", "Error retrieving master password", e)
            null
        }
    }
}