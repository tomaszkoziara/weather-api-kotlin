package controller

import data.Temperature
import org.joda.time.DateTime
import routing.ApplicationCallWrapper
import service.IWeatherService

interface IWeatherController {

    suspend fun getTemperatures(applicationCall: ApplicationCallWrapper,
                                checkParametersFunction: (request: ApplicationCallWrapper) -> Unit)

}

class WeatherController(val weatherService: IWeatherService): IWeatherController {

    override suspend fun getTemperatures(applicationCall: ApplicationCallWrapper,
                                         checkParametersFunction: (request: ApplicationCallWrapper) -> Unit) {

        checkParametersFunction(applicationCall)

        val start = applicationCall.queryParameter("start")
        val end = applicationCall.queryParameter("end")

        val startDateTime = DateTime(start)
        val endDateTime = DateTime(end)

        val temperatures: List<Temperature> = weatherService.fetchTemperatures(startDateTime.toDate(), endDateTime.toDate())
        applicationCall.respondJSON(temperatures)

    }



}