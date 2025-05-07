package com.hanif.passwordmanager.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

/**
 * DAO interface for accessing password data in the database
 */
@Dao
interface PasswordDao {
    /**
     * Gets all passwords from the database ordered by most recently updated
     */
    @Query("SELECT * FROM passwords ORDER BY updatedAt DESC")
    suspend fun getAllPasswords(): List<PasswordEntity>
    
    /**
     * Gets a specific password by ID
     */
    @Query("SELECT * FROM passwords WHERE id = :passwordId")
    suspend fun getPasswordById(passwordId: String): PasswordEntity?
    
    /**
     * Inserts a new password
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(passwordEntity: PasswordEntity)
    
    /**
     * Updates an existing password
     * Updates the updatedAt timestamp as well
     */
    @Query("UPDATE passwords SET serviceName = :serviceName, username = :username, encryptedPassword = :encryptedPassword, updatedAt = :timestamp WHERE id = :id")
    suspend fun updatePasswordFields(id: Int, serviceName: String, username: String, encryptedPassword: String, timestamp: Long = System.currentTimeMillis())
    
    /**
     * Helper method to update a password entity
     */
    @Transaction
    suspend fun updatePassword(passwordEntity: PasswordEntity) {
        updatePasswordFields(
            id = passwordEntity.id,
            serviceName = passwordEntity.serviceName,
            username = passwordEntity.username,
            encryptedPassword = passwordEntity.encryptedPassword
        )
    }
    
    /**
     * Deletes a password by ID
     */
    @Query("DELETE FROM passwords WHERE id = :passwordId")
    suspend fun deletePasswordById(passwordId: Int)
}