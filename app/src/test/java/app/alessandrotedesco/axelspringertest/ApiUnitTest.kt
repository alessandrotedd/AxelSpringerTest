package app.alessandrotedesco.axelspringertest

import app.alessandrotedesco.axelspringertest.models.NewsResponse
import app.alessandrotedesco.axelspringertest.networking.NewsApi
import app.alessandrotedesco.axelspringertest.networking.RetrofitService.createService
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class APITest {
    private val expectedNewsContentTypeHeaderResponse = "application/json; charset=utf8"
    private val httpStatusCode = 200
    private val apiKey = "API_KEY"

    @Test
    fun checkNewsResponse() {
        val apiEndpoints: NewsApi = createService(NewsApi::class.java)
        val call: Call<NewsResponse> = apiEndpoints.getNewsList(apiKey)
        try {
            val response: Response<NewsResponse> = call.execute()

            // check status code
            checkStatusCode(response.code())
            // check response body
            checkNewsResponseBody(response.body())
            // check header
            checkResponseContentType(response.headers().get("Content-Type"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkResponseContentType(contentTypeHeader: String?) {
        Assert.assertEquals(contentTypeHeader, expectedNewsContentTypeHeaderResponse)
    }

    private fun checkNewsResponseBody(body: NewsResponse?) {
        // check for null values
        Assert.assertNotNull(body)
        Assert.assertNotNull(body!!.articles)

        // check for empty list
        Assert.assertNotEquals(body.articles.size, 0)
        // check for null values
        body.articles.forEach {
            Assert.assertNotNull(it.isPaid)
            Assert.assertNotNull(it.priceIsVisible)
            Assert.assertNotNull(it.text)
            Assert.assertNotNull(it.title)
            Assert.assertNotNull(it.type)
        }
    }

    private fun checkStatusCode(responseCode: Int) {
        Assert.assertEquals(responseCode, httpStatusCode)
    }
}