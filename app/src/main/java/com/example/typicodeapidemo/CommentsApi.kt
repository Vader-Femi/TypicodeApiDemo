package com.example.typicodeapidemo

import retrofit2.Response
import retrofit2.http.GET

interface CommentsApi {

    @GET("/comments")
    suspend fun getComments(): Response<List<Comment>>

    //  If it needed an API key
    //  fun getComments(@Query("key") key: String): Response<List<Comment>>

    //  If you wanted to post to the Api
    //  @POST("/createComment")
    //  fun createComment(@Body comment: Comment): Response<CreateCommentResponse>
}