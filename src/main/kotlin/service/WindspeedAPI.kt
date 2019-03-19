package service

import data.Windspeed
import java.util.*

interface  IWindspeedAPI {
    suspend fun fetchWindspeed(atDate: Date): Windspeed
}

class WindspeedAPI(val endpoint: String) : IWindspeedAPI {

    private val apiCallAdapter = APICallAdapter()

    override suspend fun fetchWindspeed(atDate: Date): Windspeed {

        return apiCallAdapter.atDateAPICall(endpoint, atDate, Windspeed::class.java)

    }

}