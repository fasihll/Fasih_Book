package com.example.fasihbook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fasihbook.ui.theme.FasihBookTheme

@Composable
fun BookItem(
    urlToImage: String,
    title: String,
    publishedAt: String,
    modifier: Modifier = Modifier,
    width: Int
) {
    Column(modifier = modifier) {
        Box(
            modifier = modifier
                .width(width.dp)
                .clip(shape = RoundedCornerShape(5.dp))
        ) {
            AsyncImage(
                model = urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(5.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = publishedAt,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookItemPreview() {
    FasihBookTheme {
        BookItem(
            urlToImage = "https://m.media-amazon.com/images/I/61zF7+TkTOL._SY466_.jpg",
            title = "AWS: The Ultimate Guide From Beginners To Advanced For The Amazon Web " +
                    "Services (2020 Edition)",
            publishedAt = "December 21, 2019",
            width = 150
        )
    }
}