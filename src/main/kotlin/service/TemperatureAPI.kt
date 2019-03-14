package service

import com.google.gson.Gson
import common.APIError
import data.Temperature
import java.text.SimpleDateFormat
import java.util.*

interface  ITemperatureAPI {
    suspend fun fetchTemperature(atDate: Date): Temperature
}

class TemperatureAPI(val endpoint: String) : AbstractAPI(), ITemperatureAPI {

    override suspend fun fetchTemperature(atDate: Date): Temperature {

        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val url = "$endpoint/temperature?at=${isoDateFormat.format(atDate)}"

        val (resultString, error) = getCall(url)

        if (error != null) {
            throw APIError("Error fetching temperature!")
        }

        println("Fetched $resultString")

        return Gson().fromJson(resultString, Temperature::class.java)

    }

}
