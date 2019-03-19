package service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import common.APIError
import java.text.SimpleDateFormat
import java.util.*

class APICallAdapter: AbstractAPI() {

    private val isoDateFormatPatter = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val gson: Gson = GsonBuilder().setDateFormat(isoDateFormatPatter).create()

    suspend fun <T> atDateAPICall(endpoint: String, atDate: Date, clazz: Class<T>): T {

        val isoDateFormat = SimpleDateFormat(isoDateFormatPatter)
        isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val url = "$endpoint?at=${isoDateFormat.format(atDate)}"

        println("Calling url $url")
        val (resultString, error) = this.getCall(url)

        if (error != null) {
            println(error)
            throw APIError("Error fetching data!")
        }

        return gson.fromJson(resultString, clazz)

    }

}