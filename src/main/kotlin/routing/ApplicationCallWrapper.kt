package routing

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.application.ApplicationCall
import io.ktor.response.respondText

class ApplicationCallWrapper(val applicationCall: ApplicationCall) {

    private val isoDateFormatPatter = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val gson: Gson = GsonBuilder().setDateFormat(isoDateFormatPatter).create()

    fun queryParameter(parameter: String): String? {
        return applicationCall.request.queryParameters[parameter]
    }

    suspend fun respondJSON(any: Any) {
        applicationCall.respondText(gson.toJson(any))
    }

}