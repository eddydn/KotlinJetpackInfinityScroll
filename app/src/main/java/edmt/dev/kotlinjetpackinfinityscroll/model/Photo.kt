package edmt.dev.kotlinjetpackinfinityscroll.model

import com.google.gson.annotations.SerializedName

 class Photo {
    @SerializedName("description")
    var description: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("user")
    var user: Int? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("id")
    var id: Int? = null

}