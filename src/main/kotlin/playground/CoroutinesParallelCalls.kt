package playground

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {

    val responses: ArrayList<Deferred<String>> = arrayListOf()

    // Parallel calls
    runBlocking {
        for(i in 1..100) {
            responses.add(async {
                apiCall(i)
            })
        }
    }

    runBlocking {
        for (response in responses) {
            println(response.await())
        }
    }

}

suspend fun apiCall(n: Int): String {

    val delayMillis = Random.nextLong(500, 2000)
    delay(delayMillis)

    return "Call $n"

}