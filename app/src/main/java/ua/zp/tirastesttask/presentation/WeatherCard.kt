package ua.zp.tirastesttask.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ua.zp.tirastesttask.R
import ua.zp.tirastesttask.data.models.WeatherData
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    data: WeatherData,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(backgroundColor),
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = Color.White,
                modifier = Modifier.align(Alignment.End),
                text = "Today ${data.localTime}"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = data.cityName,
                fontSize = 25.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = "https:${data.icon}",
                contentDescription = null,
                modifier = modifier.width(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.condition,
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${data.temperature}°C",
                fontSize = 50.sp,
                color = Color.White
            )
            Text(
                text = "feels like ${data.feelsLike}°C",
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherDataDisplay(
                    value = data.pressure.roundToInt(),
                    unit = "hpa",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White)
                )
                WeatherDataDisplay(
                    value = data.humidity.roundToInt(),
                    unit = "%",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White)
                )
                WeatherDataDisplay(
                    value = data.windSpeed.roundToInt(),
                    unit = "km/h",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White)
                )
            }
        }
    }
}