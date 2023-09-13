package edmt.dev.kotlinjetpackinfinityscroll.interfaces

import edmt.dev.kotlinjetpackinfinityscroll.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("offset") offset: Long = 0,
        @Query("limit") limit: Int = 10
    ): ApiResponse
}