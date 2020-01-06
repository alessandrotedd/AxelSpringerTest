package app.alessandrotedesco.axelspringertest.networking

import app.alessandrotedesco.axelspringertest.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    @Headers("Content-Type:application/json; charset=UTF-8")
    fun getNewsList(
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}