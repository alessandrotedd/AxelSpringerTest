package app.alessandrotedesco.axelspringertest.networking

import androidx.lifecycle.MutableLiveData
import app.alessandrotedesco.axelspringertest.networking.RetrofitService.createService
import app.alessandrotedesco.axelspringertest.models.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {
    private val newsApi: NewsApi = createService(NewsApi::class.java)

    /**
     * makes a call to the server to get news data
     * @param key the API key token
     */
    fun getNews(key: String): MutableLiveData<NewsResponse> {
        val newsData = MutableLiveData<NewsResponse>()
        newsApi.getNewsList(key).enqueue(object : Callback<NewsResponse> {
            /**
             * handle response
             */
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful)
                    // return json response
                    newsData.value = response.body()
            }

            /**
             * handle call failure
             */
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // return empty data
                newsData.value = NewsResponse()
                // log error
                t.printStackTrace()
            }
        })
        return newsData
    }

    companion object {
        private lateinit var newsRepository: NewsRepository
        val instance: NewsRepository
            get() {
                newsRepository = NewsRepository()
                return newsRepository
            }
    }

}