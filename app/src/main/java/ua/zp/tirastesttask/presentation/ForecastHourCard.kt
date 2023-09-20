package ua.zp.tirastesttask.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import ua.zp.tirastesttask.R
import ua.zp.tirastesttask.data.models.ForecastHourData
import kotlin.math.roundToInt

@Composable
fun ForecastHourCard(
    data: ForecastHourData,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(backgroundColor),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
        ) {
            val (columnFirst, image, columnSecond, row) = createRefs()

            Column(
                modifier = modifier
                    .padding(8.dp)
                    .constrainAs(columnFirst) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(row.top)
                    },
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = data.time,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            AsyncImage(
                model = "https:${data.icon}",
                contentDescription = null,
                modifier = modifier
                    .size(64.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(columnSecond.start)
                        bottom.linkTo(row.top)
                    }
            )
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .constrainAs(columnSecond) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(row.top)
                    },
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${data.temperature}°C",
                    color = Color.White
                )
                Text(
                    text = data.condition,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(row) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
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