package routing

import common.startEndParameterCheckFun
import controller.IWeatherController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

fun Application.weatherModule() {

    val weatherController by inject<IWeatherController>()

    routing {

        get("/temperatures") {
            weatherController.getTemperatures(
                ApplicationCallWrapper(call),
                checkParametersFunction = startEndParameterCheckFun
            )
        }

        get("/windspeeds") {
            weatherController.getWindspeeds(
                ApplicationCallWrapper(call),
                checkParametersFunction = startEndParameterCheckFun
            )
        }

        get("/weather") {
            weatherController.getWeather(
                ApplicationCallWrapper(call),
                checkParametersFunction = startEndParameterCheckFun
            )
        }

    }

}