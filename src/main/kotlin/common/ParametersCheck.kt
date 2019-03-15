package common

import routing.ApplicationCallWrapper
import java.time.format.DateTimeFormatter

val startEndParameterCheckFun: (applicationCall: ApplicationCallWrapper) -> Unit = {

    val start = it.queryParameter("start")
    val end = it.queryParameter("end")

    checkIfExists("start", start)
    checkIfExists("end", end)
    checkIfValidISO8610("start", start)
    checkIfValidISO8610("end", end)

}

private fun checkIfExists(parameterName: String, parameterValue: String?) {
    if (parameterValue == null) {
        throw InputError("Parameter '$parameterName' is missing!")
    }
}

private fun checkIfValidISO8610(parameterName: String, parameterValue: String?) {
    try {
        DateTimeFormatter.ISO_DATE_TIME.parse(parameterValue)
    } catch (e: Throwable) {
        throw InputError("Parameter '$parameterName' is not a valid ISO8601 parameter!")
    }
}