package edmt.dev.kotlinjetpackinfinityscroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import edmt.dev.kotlinjetpackinfinityscroll.model.Photo
import edmt.dev.kotlinjetpackinfinityscroll.ui.theme.KotlinJetpackInfinityScrollTheme
import edmt.dev.kotlinjetpackinfinityscroll.viewmodel.MainViewModel
import edmt.dev.kotlinjetpackinfinityscroll.viewmodel.STATE

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        setContent {
            KotlinJetpackInfinityScrollTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        PhotoList(mainViewModel)
                        if (mainViewModel.photoResponse.isEmpty())
                            mainViewModel.getPhotoList()

                        if (mainViewModel.state == STATE.LOADING) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Card(
                                    modifier = Modifier
                                        .alpha(0.7f)
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.padding(50.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun PhotoList(mainViewModel: MainViewModel) {

        val scrollState = rememberLazyListState()
        val isItemReachEndScroll by remember {
            derivedStateOf {
                scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ==
                        scrollState.layoutInfo.totalItemsCount - 1
            }
        }

        LaunchedEffect(key1 = isItemReachEndScroll, block = {
            mainViewModel.loadMorePhotoList()
        })

        LazyColumn(
            state = scrollState
        ) {
            itemsIndexed(mainViewModel.photoResponse) { _, item -> PhotoItem(photo = item) }
        }
    }

    @Composable
    private fun PhotoItem(photo: Photo) {
        Card(
            modifier = Modifier
                .padding(8.dp, 4.dp)
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Surface {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = photo.url, contentDescription = "Photo",
                        modifier = Modifier.fillMaxHeight(),
                        contentScale = ContentScale.FillBounds
                    )

                    photo.title?.let {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp)
                                    .background(Color.DarkGray.copy(alpha = 0.5f)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = it,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }
}

