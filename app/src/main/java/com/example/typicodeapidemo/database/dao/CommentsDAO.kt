package com.example.typicodeapidemo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.typicodeapidemo.database.entity.Comment

@Dao
interface CommentsDAO {

    @Query("SELECT * FROM comments ORDER BY id ASC")
    fun getComments(): LiveData<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: Comment)
}