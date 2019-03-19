package controller

import org.joda.time.DateTime
import routing.ApplicationCallWrapper
import service.IWeatherService
import java.util.*

interface IWeatherController {

    suspend fun getTemperatures(applicationCall: ApplicationCallWrapper,
                                checkParametersFunction: (request: ApplicationCallWrapper) -> Unit)
    suspend fun getWindspeeds(applicationCall: ApplicationCallWrapper,
                              checkParametersFunction: (request: ApplicationCallWrapper) -> Unit)
    suspend fun getWeather(applicationCall: ApplicationCallWrapper,
                              checkParametersFunction: (request: ApplicationCallWrapper) -> Unit)

}

class WeatherController(val weatherService: IWeatherService): IWeatherController {

    override suspend fun getTemperatures(
        applicationCall: ApplicationCallWrapper,
        checkParametersFunction: (request: ApplicationCallWrapper) -> Unit
    ) {

        doWeatherCall(
            applicationCall = applicationCall,
            checkParametersFunction = checkParametersFunction,
            fetchDataFunction = weatherService::fetchTemperatures
        )

    }

    override suspend fun getWindspeeds(
        applicationCall: ApplicationCallWrapper,
        checkParametersFunction: (request: ApplicationCallWrapper) -> Unit
    ) {

        doWeatherCall(
            applicationCall = applicationCall,
            checkParametersFunction = checkParametersFunction,
            fetchDataFunction = weatherService::fetchWindspeeds
        )

    }

    override suspend fun getWeather(
        applicationCall: ApplicationCallWrapper,
        checkParametersFunction: (request: ApplicationCallWrapper) -> Unit
    ) {

        doWeatherCall(
            applicationCall = applicationCall,
            checkParametersFunction = checkParametersFunction,
            fetchDataFunction = weatherService::fetchWeather
        )

    }

    suspend private fun doWeatherCall(
        applicationCall: ApplicationCallWrapper,
        checkParametersFunction: (request: ApplicationCallWrapper) -> Unit,
        fetchDataFunction: (start: Date, end: Date) -> List<Any>
    ) {
        checkParametersFunction(applicationCall)

        val start = applicationCall.queryParameter("start")
        val end = applicationCall.queryParameter("end")

        val startDateTime = DateTime(start)
        val endDateTime = DateTime(end)

        val outputList = fetchDataFunction(startDateTime.toDate(), endDateTime.toDate())
        applicationCall.respondJSON(outputList)
    }

}