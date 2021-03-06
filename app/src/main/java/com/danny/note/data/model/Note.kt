package com.danny.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date

@Entity(
    tableName = "note"
)
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val title : String,
    val contents : String,
    val tags : List<Color>,
    val time : Long // Time in millis
) : Serializable