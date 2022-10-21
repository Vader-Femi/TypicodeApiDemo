package com.example.typicodeapidemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.typicodeapidemo.database.CommentsDatabase
import com.example.typicodeapidemo.viewmodel.CommentsViewModel
import com.example.typicodeapidemo.databinding.ActivityMainBinding
import com.example.typicodeapidemo.repository.CommentsRepository
import com.example.typicodeapidemo.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CommentsViewModel
    private val commentsAdapter = CommentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        setupRecyclerView()

        lifecycleScope.launch {
            getComments()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {

            binding.swipeRefreshLayout.isRefreshing = false

            lifecycleScope.launch {
                getComments()
            }
        }

    }

    private suspend fun getComments() {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getComments()
            } catch (e: IOException) {
                Log.e(TAG, getString(R.string.internet_connection_error))
                binding.progressBar.isVisible = false
                return
            } catch (e: HttpException) {
                Log.e(TAG, getString(R.string.unexpected_response))
                binding.progressBar.isVisible = false
                return
            }
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    viewModel.insertComment(it)
                }

            } else {
                Toast.makeText(this@MainActivity,
                    getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG).show()
            }
            binding.progressBar.isVisible = false
    }

    private fun setupRecyclerView() = binding.rvComments.apply {
        adapter = commentsAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)

        viewModel.getComments().observe(this@MainActivity) {
            if (this@MainActivity.lifecycle.currentState == Lifecycle.State.RESUMED) {
                commentsAdapter.comments = it
            }
        }
    }

    private fun setupViewModel() {
        val database: CommentsDatabase = CommentsDatabase.invoke(this)
        val commentsRepository = CommentsRepository(database)
        val viewModelFactory = ViewModelFactory(commentsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommentsViewModel::class.java]
    }
}