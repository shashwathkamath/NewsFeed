package com.kamath.newsfeed.news.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kamath.newsfeed.news.domain.model.NewsDto

@Composable
fun NewsItem(
    dto: NewsDto,
    onNewsItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = { onNewsItemClick() }
        //colors = CardDefaults.cardColors(Color.Black)
    ) {
        Text(dto.title)
        Spacer(modifier = Modifier.height(10.dp))
        AsyncImage(
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(8.dp)),
            model = dto.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}