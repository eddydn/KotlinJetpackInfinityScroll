package edmt.dev.kotlinjetpackinfinityscroll.network

import edmt.dev.kotlinjetpackinfinityscroll.interfaces.IApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private var apiService: IApiService?= null
        fun getInstance(): IApiService{
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://api.slingacademy.com/v1/sample-data/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IApiService::class.java)
            }
            return apiService!!
        }
    }
}