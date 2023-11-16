package com.example.fasihbook.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fasihbook.R
import com.example.fasihbook.di.Injection
import com.example.fasihbook.ui.ViewModelFactory
import com.example.fasihbook.ui.common.UiState
import com.example.fasihbook.ui.components.FavoriteItem
import com.example.fasihbook.ui.components.ShapeBackground
import com.example.fasihbook.ui.theme.FasihBookTheme

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetile: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedFavoriteBook()
            }

            is UiState.Success -> {
                FavoriteContent(
                    state = uiState.data,
                    onFavoriteButtonClicked = { bookId, isFavorite ->
                        viewModel.updateFavoriteBook(bookId, isFavorite)
                    },
                    navigateToDetile = navigateToDetile,
                )
            }

            is UiState.Error -> {}
        }
    }
}


@Composable
fun FavoriteContent(
    state: FavoriteState,
    onFavoriteButtonClicked: (bookId: Long, isFavorite: Boolean) -> Unit,
    navigateToDetile: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    ShapeBackground()
    Column {
        Text(
            text = "Favorite",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f).testTag("FavoriteList")
        ) {
            if (state.FavoriteBook.isEmpty()){
                item {
                    Text(
                        text = stringResource(R.string.empty_data),
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }else{
                items(state.FavoriteBook, key = { it.book.id }) { item ->
                    FavoriteItem(
                        imageUrl = item.book.image,
                        title = item.book.title,
                        description = item.book.description,
                        publishedAt = item.book.publishedAt,
                        isFavorite = item.isFavorite,
                        onAddToFavorite = { onFavoriteButtonClicked(item.book.id, !item.isFavorite) },
                        modifier = Modifier.clickable {
                            navigateToDetile(item.book.id)
                        }
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun FavoriteBookPreview() {
    FasihBookTheme {
        FavoriteScreen(navigateToDetile = {})
    }
}