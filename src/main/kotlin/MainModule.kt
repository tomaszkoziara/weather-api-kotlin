import org.koin.dsl.module.module
import service.TemperatureAPI
import controller.ITemperatureController
import controller.TemperatureController
import service.ITemperatureAPI
import service.IWeatherService
import service.WeatherService

val mainModule = module(createOnStart = true) {

    // APIs
    single { TemperatureAPI("http://localhost:8000") as ITemperatureAPI }

    // Controllers
    single { TemperatureController(get()) as ITemperatureController }

    // Services
    single { WeatherService(get()) as IWeatherService }

}