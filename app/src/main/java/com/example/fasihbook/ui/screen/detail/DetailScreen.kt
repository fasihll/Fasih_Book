package com.example.fasihbook.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fasihbook.R
import com.example.fasihbook.di.Injection
import com.example.fasihbook.ui.ViewModelFactory
import com.example.fasihbook.ui.common.UiState
import com.example.fasihbook.ui.components.FavoriteButton
import com.example.fasihbook.ui.theme.FasihBookTheme

@Composable
fun DetailScreen(
    bookId: Long,
    viewModel: DetailBookViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    onClickReadNow: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteBookById(bookId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.book.image,
                    title = data.book.title,
                    description = data.book.description,
                    publishedAt = data.book.publishedAt,
                    isFavorite = data.isFavorite,
                    onBackClick = navigateBack,
                    onAddToFavorite = { isFavorite ->
                        viewModel.addToFavorite(data.book, isFavorite)
                    },
                    onClickReadNow = onClickReadNow
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    image: String,
    title: String,
    description: String,
    publishedAt: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onAddToFavorite: (isFavorite: Boolean) -> Unit,
    onClickReadNow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var favoriteNow by rememberSaveable { mutableStateOf(isFavorite) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                        .blur(radius = 15.dp)
                        .alpha(0.5f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                        .semantics {
                            contentDescription = "Back"
                        },
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .offset(y = -200.dp)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .width(200.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 16.dp),
                )
                Text(
                    text = publishedAt,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 16.dp),
                )
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.description_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = description,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(16.dp)
        ) {
            FavoriteButton(isFavorite = favoriteNow,
                onAddToFavorite = {
                    favoriteNow = !favoriteNow
                    onAddToFavorite(favoriteNow)
                },
                modifier = modifier.semantics(
                    mergeDescendants = true
                ) {
                    contentDescription = "FavoriteButton"
                })
            Button(onClick = { onClickReadNow() }, modifier = modifier.weight(1f)) {
                Text(text = stringResource(R.string.btn_readNow))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    FasihBookTheme {
        DetailContent(image = "https://m.media-amazon.com/images/I/61zF7+TkTOL._SY466_.jpg",
            title = "AWS: The Ultimate Guide From Beginners To Advanced For The Amazon Web Services (2020" + " Edition)",
            description = "This book offers a guide to AWS, for both beginner and advanced users. If you want to reduce your companies operating costs, and control the safety of your data, use this step-by-step guide for computing and networking in the AWS cloud.",
            publishedAt = "December 21, 2019",
            isFavorite = false,
            onBackClick = {},
            onAddToFavorite = {},
            onClickReadNow = {})
    }
}