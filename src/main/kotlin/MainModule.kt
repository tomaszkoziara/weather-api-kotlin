import controller.IWeatherController
import controller.WeatherController
import org.koin.dsl.module.module
import service.*

val mainModule = module(createOnStart = true) {

    // APIs
    single { TemperatureAPI("http://localhost:8000") as ITemperatureAPI }
    single { WindspeedAPI("http://localhost:8080") as IWindspeedAPI }

    // Controllers
    single { WeatherController(get()) as IWeatherController }

    // Services
    single { WeatherService(get(), get()) as IWeatherService }

}