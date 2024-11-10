package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entities.profile.ProfileEntity

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ProfileEntity)

    @Query("select * from profile")
    fun get(): List<ProfileEntity>

    @Update
    suspend fun update(entity: ProfileEntity)

    @Query("delete from profile")
    suspend fun clear()
}