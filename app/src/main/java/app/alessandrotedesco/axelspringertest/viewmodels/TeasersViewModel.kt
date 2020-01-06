package app.alessandrotedesco.axelspringertest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.alessandrotedesco.axelspringertest.networking.NewsRepository
import app.alessandrotedesco.axelspringertest.models.NewsResponse

class TeasersViewModel : ViewModel() {
    private lateinit var mutableLiveData: MutableLiveData<NewsResponse>
    private lateinit var newsRepository: NewsRepository
    fun init() {
        newsRepository = NewsRepository.instance
        mutableLiveData = newsRepository.getNews("API_KEY")
    }

    fun getNewsRepository(): LiveData<NewsResponse> {
        return mutableLiveData
    }
}