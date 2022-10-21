package com.example.typicodeapidemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.typicodeapidemo.database.entity.Comment
import com.example.typicodeapidemo.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentsViewModel(private val commentsRepository: CommentsRepository) : ViewModel() {

    fun insertComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            commentsRepository.insertComments(comment)
        }
    }

    fun getComments(): LiveData<List<Comment>> =
        commentsRepository.getComments()
}