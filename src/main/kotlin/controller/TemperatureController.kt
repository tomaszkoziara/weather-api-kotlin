package controller

import com.google.gson.Gson
import data.Temperature
import io.ktor.application.ApplicationCall
import io.ktor.request.ApplicationRequest
import io.ktor.response.respondText
import org.joda.time.DateTime
import service.IWeatherService

interface ITemperatureController {

    suspend fun getTemperatures(call: ApplicationCall, checkParametersFunction: (request: ApplicationRequest) -> Unit)

}

class TemperatureController(val weatherService: IWeatherService): ITemperatureController {

    override suspend fun getTemperatures(call: ApplicationCall,
                                         checkParametersFunction: (request: ApplicationRequest) -> Unit) {

        checkParametersFunction(call.request)

        val start = call.request.queryParameters["start"]
        val end = call.request.queryParameters["end"]

        val startDateTime = DateTime(start)
        val endDateTime = DateTime(end)

        val temperatures: List<Temperature> = weatherService.fetchTemperatures(startDateTime.toDate(), endDateTime.toDate())
        call.respondText(Gson().toJson(temperatures))

    }



}