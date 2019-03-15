package service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import common.APIError
import data.Temperature
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

interface  ITemperatureAPI {
    suspend fun fetchTemperature(atDate: Date): Temperature
}

class TemperatureAPI(val endpoint: String) : AbstractAPI(), ITemperatureAPI {

    private val isoDateFormatPatter = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val gson: Gson = GsonBuilder().setDateFormat(isoDateFormatPatter).create()

    override suspend fun fetchTemperature(atDate: Date): Temperature {

        val isoDateFormat = SimpleDateFormat(isoDateFormatPatter)
        isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val url = "$endpoint/temperature?at=${isoDateFormat.format(atDate)}"

        val (resultString, error) = getCall(url)

        if (error != null) {
            throw APIError("Error fetching temperature!")
        }

        return gson.fromJson(resultString, Temperature::class.java)

    }

}