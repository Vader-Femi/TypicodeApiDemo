package com.example.typicodeapidemo.repository

import androidx.lifecycle.LiveData
import com.example.typicodeapidemo.database.CommentsDatabase
import com.example.typicodeapidemo.database.entity.Comment

class CommentsRepository(private val database: CommentsDatabase) {

    fun getComments(): LiveData<List<Comment>> =
        database.commentsDAO().getComments()

    fun insertComments(comment: Comment) {
        database.commentsDAO().insertComment(comment)
    }
}