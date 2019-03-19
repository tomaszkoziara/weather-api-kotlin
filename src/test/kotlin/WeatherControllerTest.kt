import common.InputError
import controller.WeatherController
import data.Temperature
import data.Weather
import data.Windspeed
import io.kotlintest.Spec
import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldThrowExactly
import io.kotlintest.specs.StringSpec
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import routing.ApplicationCallWrapper
import service.IWeatherService

lateinit var weatherService: IWeatherService
lateinit var applicationCall: ApplicationCallWrapper

private val testListener = object : TestListener {

    override fun beforeSpec(spec: Spec) {

        weatherService = spyk()
        applicationCall = mockk(relaxed = true)

    }

}

private fun setInputParameters(startDate: String, endDate: String) {
    every { applicationCall.queryParameter("start") } returns startDate
    every { applicationCall.queryParameter("end") } returns endDate
}

class WeatherControllerTest : StringSpec() {

    override fun listeners(): List<TestListener> = listOf(testListener)

    init {

        "Get temperatures functionality should call temperature service and return fetched temperatures" {

            setInputParameters("2008-09-15T00:00:00Z", "2008-09-16T00:00:00Z")

            val temperatures = arrayListOf(
                Temperature(
                    date = DateTime("2008-09-15T00:00:00Z").toDate(),
                    temp = 13.545966460476553
                ),
                Temperature(
                    date = DateTime("2008-09-16T00:00:00Z").toDate(),
                    temp = 15.790548041340848
                )
            )

            every {
                weatherService.fetchTemperatures(
                    DateTime("2008-09-15T00:00:00Z").toDate(),
                    DateTime("2008-09-16T00:00:00Z").toDate()
                )
            } returns temperatures

            WeatherController(weatherService).getTemperatures(applicationCall, {})

            coVerify {
                applicationCall.respondJSON(temperatures)
            }

        }

        "Get temperatures functionality should throw an error because of parameters validation" {

            shouldThrowExactly<InputError> {

                setInputParameters("2008-09-15T00:00:00Z", "2008-09-16T00:00:00Z")

                runBlocking {
                    WeatherController(weatherService).getTemperatures(applicationCall) {
                        throw InputError("I don't like the parameters")
                    }
                }

            }

        }

        "Get windspeeds functionality should call windspeed service and return fetched windspeeds" {

            setInputParameters("2008-09-15T00:00:00Z", "2008-09-17T00:00:00Z")

            val windspeeds = arrayListOf(
                Windspeed(
                    date = DateTime("2008-09-15T00:00:00Z").toDate(),
                    west = -5.45,
                    north = -7.95
                ),
                Windspeed(
                    date = DateTime("2008-09-16T00:00:00Z").toDate(),
                    west = 7.92,
                    north = 1.10
                ),
                Windspeed(
                    date = DateTime("2008-09-17T00:00:00Z").toDate(),
                    west = -2.22,
                    north = -3.33
                )
            )

            every {
                weatherService.fetchWindspeeds(
                    DateTime("2008-09-15T00:00:00Z").toDate(),
                    DateTime("2008-09-17T00:00:00Z").toDate()
                )
            } returns windspeeds

            WeatherController(weatherService).getWindspeeds(applicationCall, {})

            coVerify {
                applicationCall.respondJSON(windspeeds)
            }

        }

        "Get windspeeds functionality should throw an error because of parameters validation" {

            shouldThrowExactly<InputError> {

                setInputParameters("2008-09-10T00:00:00Z", "2008-09-12T00:00:00Z")

                runBlocking {
                    WeatherController(weatherService).getWindspeeds(applicationCall) {
                        throw InputError("I don't like the parameters")
                    }
                }

            }

        }

        "Get weather functionality should call weather service and return fetched weather list" {

            setInputParameters("1990-09-11T00:00:00Z", "1990-09-13T00:00:00Z")

            val weatherList = arrayListOf(
                Weather(
                    date = DateTime("1990-09-11T00:00:00Z").toDate(),
                    temp = 12.10,
                    north = 0.75,
                    west = 2.85
                ),
                Weather(
                    date = DateTime("1990-09-12T00:00:00Z").toDate(),
                    temp = 8.84,
                    north = -4.64,
                    west = 1.21
                ),
                Weather(
                    date = DateTime("1990-09-13T00:00:00Z").toDate(),
                    temp = 6.24,
                    north = 6.33,
                    west = -5.53
                )
            )

            every {
                weatherService.fetchWeather(
                    DateTime("1990-09-11T00:00:00Z").toDate(),
                    DateTime("1990-09-13T00:00:00Z").toDate()
                )
            } returns weatherList

            WeatherController(weatherService).getWeather(applicationCall, {})

            coVerify {
                applicationCall.respondJSON(weatherList)
            }

        }

        "Get weather functionality should throw an error because of parameters validation" {

            shouldThrowExactly<InputError> {

                setInputParameters("2019-03-1T00:00:00Z", "2019-03-18T00:00:00Z")

                runBlocking {
                    WeatherController(weatherService).getWeather(applicationCall) {
                        throw InputError("I don't like the parameters")
                    }
                }

            }

        }

    }

}