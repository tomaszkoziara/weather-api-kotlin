import common.InputError
import controller.WeatherController
import data.Temperature
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

class WeatherControllerTest: StringSpec() {

    override fun listeners(): List<TestListener> = listOf(testListener)

    init {

        "Should call temperature service and return fetched temperatures" {

            val temperatures = arrayListOf(
                Temperature(date = DateTime("2008-09-15T00:00:00Z").toDate(), temp = 13.545966460476553),
                Temperature(date = DateTime("2008-09-16T00:00:00Z").toDate(), temp = 15.790548041340848)
            )

            setInputParameters("2008-09-15T00:00:00Z", "2008-09-16T00:00:00Z")

            every { weatherService.fetchTemperatures(DateTime("2008-09-15T00:00:00Z").toDate(),
                DateTime("2008-09-16T00:00:00Z").toDate())
            } returns temperatures

            WeatherController(weatherService).getTemperatures(applicationCall, {})

            coVerify {
                applicationCall.respondJSON(temperatures)
            }

        }

        "Should throw an error because of parameters validation" {

            shouldThrowExactly<InputError> {

                setInputParameters("2008-09-15T00:00:00Z", "2008-09-16T00:00:00Z")

                runBlocking {
                    WeatherController(weatherService).getTemperatures(applicationCall) {
                        throw InputError("I don't like the parameters")
                    }
                }

            }

        }

    }

}