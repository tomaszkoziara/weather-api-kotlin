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

        val weatherList = arrayListOf<Weather>()

        runBlocking {

            val temperaturesAsync = async { fetchTemperatures(start, end) }
            val windspeedsAsync = async { fetchWindspeeds(start, end) }

            val temperatures = temperaturesAsync.await()
            val windspeeds = windspeedsAsync.await()

            for (i in 0 until temperatures.size) {
                weatherList.add(buildWeather(temperatures[i], windspeeds[i]))
            }
        }

        return weatherList
    }

    private fun buildWeather(temperature: Temperature, windspeed: Windspeed): Weather {
        return Weather(
            temp = temperature.temp,
            date = temperature.date,
            north = windspeed.north,
            west = windspeed.west
        )
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