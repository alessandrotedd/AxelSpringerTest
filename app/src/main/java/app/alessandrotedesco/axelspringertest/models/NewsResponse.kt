package app.alessandrotedesco.axelspringertest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsResponse {
    @SerializedName("name") @Expose val name: String = "" // headlines
    @SerializedName("teasers") @Expose val articles = ArrayList<Teaser>()
}