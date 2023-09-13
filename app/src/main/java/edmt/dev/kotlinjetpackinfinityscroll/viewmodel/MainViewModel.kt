package edmt.dev.kotlinjetpackinfinityscroll.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edmt.dev.kotlinjetpackinfinityscroll.model.Photo
import edmt.dev.kotlinjetpackinfinityscroll.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class STATE {
    LOADING,
    SUCCESS,
    FAILED
}

class MainViewModel : ViewModel() {
     var photoResponse : List<Photo> by mutableStateOf(listOf())
    private var lastOffset:Long by mutableLongStateOf(0.toLong())
    private var errorMessage: String by mutableStateOf("")

    var state by mutableStateOf(STATE.LOADING)

    fun getPhotoList() {
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try{
                val apiResponse = apiService.getPhotos(lastOffset)
                photoResponse = apiResponse.photos
                lastOffset = photoResponse.size.toLong()
                state = STATE.SUCCESS
            }catch (e: Exception){
                errorMessage = e.message.toString()
                state = STATE.FAILED
            }
        }
    }

    fun loadMorePhotoList() {
        viewModelScope.launch {
            state = STATE.LOADING
            delay(5000)
            val apiService = RetrofitClient.getInstance()
            try{
                val apiResponse = apiService.getPhotos(lastOffset)
                photoResponse = photoResponse.plus(apiResponse.photos)
                lastOffset = photoResponse.size.toLong()
                state = STATE.SUCCESS
            }catch (e: Exception){
                errorMessage = e.message.toString()
                state = STATE.FAILED
            }
        }
    }

}