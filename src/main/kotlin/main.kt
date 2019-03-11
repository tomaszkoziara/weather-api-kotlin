import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ping.pingModule

fun main(args: Array<String>) {

    val port = args.getOrNull(1)?.let {
        Integer.parseInt(it)
    } ?: 3000

    println("Starting server on port $port")
    embeddedServer(Netty, port = port) {
        pingModule()
    }.start(wait = true)

}