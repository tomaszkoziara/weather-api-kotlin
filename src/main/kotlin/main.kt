import common.InputError
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.installKoin
import routing.pingModule
import routing.temperaturesModule

fun main(args: Array<String>) {

    val port = args.getOrNull(1)?.let {
        Integer.parseInt(it)
    } ?: 3000

    println("Starting server on port $port")
    embeddedServer(Netty, port = port, module = Application::main).start(wait = true)

}

fun Application.main() {

    install(DefaultHeaders)
    install(CallLogging)
    installKoin(listOf(mainModule))
    install(StatusPages) {
        exception<InputError> { error ->
            call.respondText(error.message)
        }
        exception<Throwable> { error ->
            call.respond(HttpStatusCode.InternalServerError, "Something bad happened!")
            log.error(error.message, error)
        }
    }

    pingModule()
    temperaturesModule()

}