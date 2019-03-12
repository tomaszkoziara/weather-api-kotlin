package temperature

import io.ktor.application.ApplicationCall
import io.ktor.request.ApplicationRequest
import io.ktor.response.respondText

interface ITemperatureController {

    suspend fun getTemperatures(call: ApplicationCall, checkParametersFunction: (request: ApplicationRequest) -> Unit)

}

class TemperatureController: ITemperatureController {

    override suspend fun getTemperatures(call: ApplicationCall,
                                         checkParametersFunction: (request: ApplicationRequest) -> Unit) {

        checkParametersFunction(call.request)

        val start = call.request.queryParameters["start"]
        val end = call.request.queryParameters["end"]

        call.respondText("Starting from ${start} to ${end}")

    }



}