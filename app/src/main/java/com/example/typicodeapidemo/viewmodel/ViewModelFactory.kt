package com.example.typicodeapidemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.typicodeapidemo.repository.CommentsRepository

class ViewModelFactory(private val repository: CommentsRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(CommentsViewModel::class.java) -> CommentsViewModel(
                repository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}