package service

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.result.Result
import java.util.*

abstract class AbstractAPI {

    private val cache: HashMap<String, Result<String, FuelError>> = hashMapOf()

    suspend fun getCall(url: String): Result<String, FuelError> {
        if (cache.containsKey(url)) {
            return cache[url]!!
        }

        val (_, _, result) = Fuel.get(url)
            .awaitStringResponseResult()
        cache[url] = result

        return result
    }

}