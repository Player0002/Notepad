package com.danny.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "color"
)
data class Color (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null, val r : Int, val g : Int, val b : Int, var name : String)