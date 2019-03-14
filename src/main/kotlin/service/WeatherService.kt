package service

import common.datesTo
import data.Temperature
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import java.util.*

interface  IWeatherService {
    fun fetchTemperatures(start: Date, end: Date): List<Temperature>
}

class WeatherService(val temperatureAPI: ITemperatureAPI): IWeatherService {

    override fun fetchTemperatures(start: Date, end: Date): List<Temperature> {

        val startDateTime = DateTime(start)
        val endDateTime = DateTime(end)
        val dates = startDateTime.datesTo(endDateTime)

        val temperaturesAsync = arrayListOf<Deferred<Temperature>>()

        // Parallel calls to the API
        runBlocking {
            for (date in dates) {
                temperaturesAsync.add(async {
                    temperatureAPI.fetchTemperature(date.toDate())
                })
            }
        }

        // Await for all the asyncs and map the array to the results
        return runBlocking {
            temperaturesAsync.map { it.await() }
        }

    }

}