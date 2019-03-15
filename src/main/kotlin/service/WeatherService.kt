package service

import common.datesTo
import data.Temperature
import data.Weather
import data.Windspeed
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import java.util.*

interface  IWeatherService {
    fun fetchTemperatures(start: Date, end: Date): List<Temperature>
    fun fetchWindspeeds(start: Date, end: Date): List<Windspeed>
    fun fetchWeather(start: Date, end: Date): List<Weather>
}

class WeatherService(val temperatureAPI: ITemperatureAPI,
    val windspeedAPI: IWindspeedAPI): IWeatherService {

    override fun fetchTemperatures(start: Date, end: Date): List<Temperature> {

        return doParallelCalls(start, end) {
            temperatureAPI.fetchTemperature(it)
        }

    }

    override fun fetchWindspeeds(start: Date, end: Date): List<Windspeed> {

        return doParallelCalls(start, end) {
            windspeedAPI.fetchWindspeed(it)
        }

    }

    override fun fetchWeather(start: Date, end: Date): List<Weather> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun <T> doParallelCalls(start: Date, end: Date, call: suspend (d: Date) -> T): List<T> {

        val startDateTime = DateTime(start)
        val endDateTime = DateTime(end)
        val dates = startDateTime.datesTo(endDateTime)

        val resultsAsync = arrayListOf<Deferred<T>>()

        // Parallel calls to the API
        runBlocking {
            for (date in dates) {
                resultsAsync.add(async {
                    call(date.toDate())
                })
            }
        }

        // Await for all the asyncs and map the array to the results
        return runBlocking {
            resultsAsync.map { it.await() }
        }

    }

}