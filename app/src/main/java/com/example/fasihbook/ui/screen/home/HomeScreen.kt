package com.example.fasihbook.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fasihbook.R
import com.example.fasihbook.di.Injection
import com.example.fasihbook.model.FavoriteBook
import com.example.fasihbook.ui.ViewModelFactory
import com.example.fasihbook.ui.common.UiState
import com.example.fasihbook.ui.components.BookItem
import com.example.fasihbook.ui.components.ShapeBackground

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetile: (Long) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBooks(query)
            }

            is UiState.Success -> {
                HomeContent(
                    favoriteBook = uiState.data,
                    modifier = modifier,
                    navigateToDetile = navigateToDetile,
                    query = query,
                    onQuerychange = {
                        query = it
                        viewModel.getAllBooks(it)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    favoriteBook: List<FavoriteBook>,
    modifier: Modifier = Modifier,
    navigateToDetile: (Long) -> Unit,
    query: String,
    onQuerychange: (String) -> Unit,
) {
    ShapeBackground()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(140.dp),
        contentPadding = PaddingValues(16.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.testTag("BookList")
    ) {
        item(
            key = "search_bar",
            span = StaggeredGridItemSpan.Companion.FullLine
        ) {
            Column {
                Text(
                    text = stringResource(R.string.welcome),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                SearchBarLayout(
                    query = query,
                    onQuerychange = onQuerychange
                )
            }
        }
        if (favoriteBook.isEmpty()){
           item(
               span = StaggeredGridItemSpan.Companion.FullLine,
           ) {
               Column (
                   horizontalAlignment = Alignment.CenterHorizontally,
               ){
                   Text(text = stringResource(R.string.empty_data), fontWeight = FontWeight.Bold)
               }
           }
        }else{
            items(favoriteBook, key = { it.book.id }) { data ->
                BookItem(
                    urlToImage = data.book.image,
                    title = data.book.title,
                    publishedAt = data.book.publishedAt,
                    width = 300,
                    modifier = Modifier.clickable {
                        navigateToDetile(data.book.id)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarLayout(
    query: String,
    onQuerychange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        query = query,
        onQueryChange = onQuerychange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(text = stringResource(R.string.search_hint))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .padding(bottom = 16.dp)
    ) {

    }
}