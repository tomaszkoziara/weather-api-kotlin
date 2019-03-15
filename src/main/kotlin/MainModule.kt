import org.koin.dsl.module.module
import service.TemperatureAPI
import controller.IWeatherController
import controller.WeatherController
import service.ITemperatureAPI
import service.IWeatherService
import service.WeatherService

val mainModule = module(createOnStart = true) {

    // APIs
    single { TemperatureAPI("http://localhost:8000") as ITemperatureAPI }

    // Controllers
    single { WeatherController(get()) as IWeatherController }

    // Services
    single { WeatherService(get()) as IWeatherService }

}