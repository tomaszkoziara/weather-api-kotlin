package routing

import common.startEndParameterCheckFun
import controller.ITemperatureController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

fun Application.temperaturesModule() {

    val temperatureController by inject<ITemperatureController>()

    routing {
        get("/temperatures") {
            temperatureController.getTemperatures(call, checkParametersFunction = startEndParameterCheckFun)
        }
    }

}