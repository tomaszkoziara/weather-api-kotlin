package playground

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {

    // Sequential loop
    runBlocking {
        for (i in 1..100) {
            // Calls are async and they produce a result as soon as they terminate
            // hence the results are unordered
            async {
                println(anotherApiCall(i))
            }
        }
    }

}

suspend fun anotherApiCall(n: Int): String {

    val delayMillis = Random.nextLong(500, 2000)
    delay(delayMillis)

    return "Call $n"

}