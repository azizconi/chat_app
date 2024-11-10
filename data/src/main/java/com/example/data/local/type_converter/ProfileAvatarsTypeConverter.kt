package com.example.data.local.type_converter

import androidx.room.TypeConverter
import com.example.data.local.entities.profile.ProfileAvatarsEntity
import com.google.gson.Gson

class ProfileAvatarsTypeConverter {

    @TypeConverter
    fun toProfileAvatar(json: String): ProfileAvatarsEntity {
        return Gson().fromJson(json, ProfileAvatarsEntity::class.java)
    }
    @TypeConverter
    fun fromProfileAvatar(entity: ProfileAvatarsEntity): String {
        return Gson().toJson(entity)
    }

}