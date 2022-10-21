package com.example.typicodeapidemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.typicodeapidemo.database.dao.CommentsDAO
import com.example.typicodeapidemo.database.entity.Comment

@Database(entities = [Comment::class], version = 1, exportSchema = false)
abstract class CommentsDatabase: RoomDatabase() {

    abstract fun commentsDAO(): CommentsDAO

    companion object {
        private const val databaseName = "typicodeapidemo.database.comments"

        private var instance: CommentsDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            CommentsDatabase::class.java,
            databaseName
        ).fallbackToDestructiveMigration()
            .build()
    }
}