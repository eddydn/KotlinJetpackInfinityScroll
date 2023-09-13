package edmt.dev.kotlinjetpackinfinityscroll.model

import com.google.gson.annotations.SerializedName

 class ApiResponse {
    @SerializedName("success")
    var success: Boolean? = null
    @SerializedName("total_photos")
    var totalPhotos: Int? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("offset")
    var offset: Int? = null
    @SerializedName("limit")
    var limit: Int? = null
    @SerializedName("photos")
    var photos: ArrayList<Photo> = arrayListOf()
}