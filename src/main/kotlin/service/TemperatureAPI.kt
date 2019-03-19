package service

import data.Temperature
import java.util.*

interface  ITemperatureAPI {
    suspend fun fetchTemperature(atDate: Date): Temperature
}

class TemperatureAPI(val endpoint: String) : ITemperatureAPI {

    private val apiCallAdapter = APICallAdapter()

    override suspend fun fetchTemperature(atDate: Date): Temperature {

        return apiCallAdapter.atDateAPICall(endpoint, atDate, Temperature::class.java)

    }

}