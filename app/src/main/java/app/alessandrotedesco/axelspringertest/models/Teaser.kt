package app.alessandrotedesco.axelspringertest.models

import app.alessandrotedesco.axelspringertest.adapters.RecyclerItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Teaser : RecyclerItem() {
    var priceIsVisible = false
    @SerializedName("title") @Expose val title: String = ""
    @SerializedName("text") @Expose val text: String = ""
    @SerializedName("isPaid") @Expose val isPaid = false
    @SerializedName("type") @Expose val type: String = ""
}