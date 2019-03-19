import controller.IWeatherController
import controller.WeatherController
import org.koin.dsl.module.module
import service.*

val mainModule = module(createOnStart = true) {

    val temperatureAPIEndpoint: String = System.getenv("temperatureAPIEndpoint") ?: "http://localhost:8000"
    val windspeedAPIEndpoint: String = System.getenv("windspeedAPIEndpoint") ?: "http://localhost:8080"

    // APIs
    single { TemperatureAPI(temperatureAPIEndpoint) as ITemperatureAPI }
    single { WindspeedAPI(windspeedAPIEndpoint) as IWindspeedAPI }

    // Controllers
    single { WeatherController(get()) as IWeatherController }

    // Services
    single { WeatherService(get(), get()) as IWeatherService }

}