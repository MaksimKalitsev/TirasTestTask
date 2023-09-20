package ua.zp.tirastesttask.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import ua.zp.tirastesttask.data.models.ForecastDayData


@Composable
fun ForecastCard(
    data: ForecastDayData,
    backgroundColor: Color,
    onClick:()->Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(backgroundColor),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
            .clickable(onClick = onClick)

    ) {
            ConstraintLayout(
                modifier = modifier
                    .fillMaxSize()
            ) {
                val (columnFirst, image, columnSecond) = createRefs()

                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .constrainAs(columnFirst) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = data.date,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        text = data.condition,
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
                            bottom.linkTo(parent.bottom)
                        }
                )
                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .constrainAs(columnSecond) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        },
                    horizontalAlignment = Alignment.End
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
