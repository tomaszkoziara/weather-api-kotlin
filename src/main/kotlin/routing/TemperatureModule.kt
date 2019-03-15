package routing

import common.startEndParameterCheckFun
import controller.IWeatherController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

fun Application.temperaturesModule() {

    val temperatureController by inject<IWeatherController>()

    routing {
        get("/temperatures") {
            temperatureController.getTemperatures(ApplicationCallWrapper(call),
                checkParametersFunction = startEndParameterCheckFun)
        }
    }

}