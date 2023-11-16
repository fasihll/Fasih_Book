package com.example.fasihbook.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fasihbook.ui.theme.FasihBookTheme

@Composable
fun FavoriteItem(
    imageUrl: String,
    title: String,
    description: String,
    publishedAt: String,
    isFavorite: Boolean,
    onAddToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Row(
            modifier = modifier
        ) {
            Box(
                modifier = modifier
                    .width(100.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(text = description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = publishedAt, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            FavoriteButton(
                isFavorite = isFavorite,
                onAddToFavorite = onAddToFavorite,
                modifier = modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .semantics(mergeDescendants = true) {
                        contentDescription = "DeleteFavorite"
                    }
            )
        }
    }
}

@Preview
@Composable
fun FavoriteItemPreview() {
    FasihBookTheme {
        FavoriteItem(imageUrl = "https://m.media-amazon.com/images/I/61zF7+TkTOL._SY466_.jpg",
            title = "AWS: The Ultimate Guide From Beginners To Advanced For The Amazon Web " + "Services (2020 Edition)",
            description = "This book offers a guide to AWS, for both beginner and advanced users. If you want to reduce your companies operating costs, and control the safety of your data, use this step-by-step guide for computing and networking in the AWS cloud.",
            publishedAt = "December 21, 2019",
            isFavorite = true,
            onAddToFavorite = {})
    }
}