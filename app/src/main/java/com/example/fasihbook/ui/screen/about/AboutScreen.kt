package com.example.fasihbook.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fasihbook.R
import com.example.fasihbook.ui.components.ShapeBackground
import com.example.fasihbook.ui.theme.FasihBookTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    ShapeBackground()
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.about_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        )
        profile(
            modifier = modifier.offset(y = 30.dp)
        )

    }
}


@Composable
fun profile(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "about_page",
            modifier = modifier
                .clip(shape = CircleShape)
                .size(120.dp)
        )
        Text(
            modifier = modifier,
            text = stringResource(R.string.about_username),
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = modifier,
            text = stringResource(R.string.about_email),
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    FasihBookTheme {
        AboutScreen()
    }
}