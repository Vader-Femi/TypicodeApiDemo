package com.example.typicodeapidemo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val body: String,
    val email: String,
    val name: String,
    val postId: Int
    ): Serializable