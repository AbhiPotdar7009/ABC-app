import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abc_app.R
import com.example.abc_app.ui.viewmodel.MainViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.livedata.observeAsState
import com.example.abc_app.domain.model.ListItem
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    var isReady by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isReady = true
    }

    if (!isReady) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        MainScreenContent(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(viewModel: MainViewModel) {
    val items by viewModel.items.observeAsState(emptyList())

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var top3Chars by remember { mutableStateOf<List<Pair<Char, Int>>>(emptyList()) }
    var query by remember { mutableStateOf("") }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            StatsBottomSheetContent(
                labelList = items,
                top3 = top3Chars
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            CarouselPager(viewModel, onPageChanged = { query = "" } )

            TextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.search(it)
                },
                placeholder = { Text("Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(items) { item ->
                    ListItemCard(item)
                }
            }
        }

        FloatingActionButton(
            onClick = {
                scope.launch {
                    top3Chars = withContext(Dispatchers.Default) {
                        viewModel.top3(items)
                    }
                    showBottomSheet = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "Stats")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselPager(viewModel: MainViewModel, onPageChanged: () -> Unit) {
    val images = listOf(R.drawable.apple, R.drawable.banana, R.drawable.cherry)
    val pagerState = rememberPagerState(pageCount = { images.size }, initialPage = 0)

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateCarousel(pagerState.currentPage)
        onPageChanged()
    }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Page Indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                val color = if (pagerState.currentPage == index) Color.Gray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}

@Composable
fun ListItemCard(item: ListItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
                Text(text = item.subtitle, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun StatsBottomSheetContent(labelList: List<ListItem>, top3: List<Pair<Char, Int>>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "List 1 (${labelList.size} items)",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        top3.forEach { (char, count) ->
            Text(text = "$char = $count")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}







