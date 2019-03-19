import data.Temperature
import data.Weather
import data.Windspeed
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.joda.time.DateTime
import service.TemperatureAPI
import service.WeatherService
import service.WindspeedAPI

class WeatherServiceTest : StringSpec() {

    lateinit var weatherService: WeatherService
    lateinit var temperatureAPI: TemperatureAPI
    lateinit var windspeedAPI: WindspeedAPI

    private val testListener = object : TestListener {

        override fun beforeSpec(spec: Spec) {

            temperatureAPI = mockk(relaxed = true)
            windspeedAPI = mockk(relaxed = true)

            weatherService = WeatherService(temperatureAPI, windspeedAPI)

            addMockTemperature("2008-09-15T00:00:00Z", 10.52)
            addMockTemperature("2008-09-16T00:00:00Z", 15.63)
            addMockTemperature("2008-09-17T00:00:00Z", 14.96)

            addMockWindspeed("2008-09-15T00:00:00Z", 4.35, 6.42)
            addMockWindspeed("2008-09-16T00:00:00Z", 7.42, -4.23)
            addMockWindspeed("2008-09-17T00:00:00Z", -1.24, -8.42)

        }

        override fun beforeTest(testCase: TestCase) {

            clearMocks(temperatureAPI, recordedCalls = true, answers = false)
            clearMocks(windspeedAPI, recordedCalls = true, answers = false)

        }

    }

    private fun addMockTemperature(date: String, temp: Double) {
        coEvery {
            temperatureAPI.fetchTemperature(DateTime(date).toDate())
        } returns Temperature(temp = temp, date = DateTime(date).toDate())
    }

    private fun addMockWindspeed(date: String, north: Double, west: Double) {
        coEvery {
            windspeedAPI.fetchWindspeed(DateTime(date).toDate())
        } returns Windspeed(north = north, west = west, date = DateTime(date).toDate())
    }

    override fun listeners(): List<TestListener> = listOf(testListener)

    init {

        "fetch temperatures functionality should fetch temperatures from API" {

            val temperatures = weatherService.fetchTemperatures(
                DateTime("2008-09-15T00:00:00Z").toDate(),
                DateTime("2008-09-17T00:00:00Z").toDate()
            )

            coVerify(exactly = 3) {
                temperatureAPI.fetchTemperature(any())
            }
            coVerify(exactly = 0) {
                windspeedAPI.fetchWindspeed(any())
            }
            temperatures.size shouldBe 3
            temperatures[0] shouldBe Temperature(
                temp = 10.52,
                date = DateTime("2008-09-15T00:00:00Z").toDate()
            )
            temperatures[1] shouldBe Temperature(
                temp = 15.63,
                date = DateTime("2008-09-16T00:00:00Z").toDate()
            )
            temperatures[2] shouldBe Temperature(
                temp = 14.96,
                date = DateTime("2008-09-17T00:00:00Z").toDate()
            )

        }

        "fetch windspeeds functionality should fetch windspeeds from API" {

            val windspeeds = weatherService.fetchWindspeeds(
                DateTime("2008-09-15T00:00:00Z").toDate(),
                DateTime("2008-09-17T00:00:00Z").toDate()
            )

            coVerify(exactly = 3) {
                windspeedAPI.fetchWindspeed(any())
            }
            coVerify(exactly = 0) {
                temperatureAPI.fetchTemperature(any())
            }
            windspeeds.size shouldBe 3
            windspeeds[0] shouldBe Windspeed(
                north = 4.35,
                west = 6.42,
                date = DateTime("2008-09-15T00:00:00Z").toDate()
            )
            windspeeds[1] shouldBe Windspeed(
                north = 7.42,
                west = -4.23,
                date = DateTime("2008-09-16T00:00:00Z").toDate()
            )
            windspeeds[2] shouldBe Windspeed(
                north = -1.24,
                west = -8.42,
                date = DateTime("2008-09-17T00:00:00Z").toDate()
            )

        }

        "fetch weather functionality should fetch weather from API" {

            val weatherList = weatherService.fetchWeather(
                DateTime("2008-09-15T00:00:00Z").toDate(),
                DateTime("2008-09-17T00:00:00Z").toDate()
            )

            coVerify(exactly = 3) {
                windspeedAPI.fetchWindspeed(any())
                temperatureAPI.fetchTemperature(any())
            }
            weatherList.size shouldBe 3
            weatherList[0] shouldBe Weather(
                temp = 10.52,
                north = 4.35,
                west = 6.42,
                date = DateTime("2008-09-15T00:00:00Z").toDate()
            )
            weatherList[1] shouldBe Weather(
                temp = 15.63,
                north = 7.42,
                west = -4.23,
                date = DateTime("2008-09-16T00:00:00Z").toDate()
            )
            weatherList[2] shouldBe Weather(
                temp = 14.96,
                north = -1.24,
                west = -8.42,
                date = DateTime("2008-09-17T00:00:00Z").toDate()
            )

        }

    }

}