package ua.zp.tirastesttask.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ua.zp.tirastesttask.data.models.ForecastData


@Composable
fun ForecastCard(
    data: ForecastData,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(backgroundColor),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = data.date,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = data.conditional,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            AsyncImage(
                model = "https:${data.icon}",
                contentDescription = null,
                modifier = modifier.width(80.dp)
            )
            Column(
                modifier = modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${data.maxtemp_c}°C",
                    color = Color.White
                )
                Text(
                    text = "${data.mintemp_c}°C",
                    color = Color.White
                )
            }
        }
    }
}