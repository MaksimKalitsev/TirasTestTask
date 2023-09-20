package ua.zp.tirastesttask.presentation.detailsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.zp.tirastesttask.data.models.ForecastHourData
import ua.zp.tirastesttask.presentation.ForecastHourCard
import ua.zp.tirastesttask.presentation.ui.theme.DarkBlue
import ua.zp.tirastesttask.presentation.ui.theme.DeepBlue


@Composable
fun DetailsScreen(forecastHourData: List<ForecastHourData>){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ){
        LazyColumn {
            items(forecastHourData) { forecastHourData ->
                ForecastHourCard(
                    data = forecastHourData,
                    backgroundColor = DeepBlue
                )
            }
        }
    }
}