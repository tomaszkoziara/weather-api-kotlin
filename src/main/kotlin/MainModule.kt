import org.koin.dsl.module.module
import temperature.ITemperatureController
import temperature.TemperatureController

val mainModule = module(createOnStart = true) {

    single { TemperatureController() as ITemperatureController }

}