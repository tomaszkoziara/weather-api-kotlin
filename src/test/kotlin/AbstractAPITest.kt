import com.github.kittinunf.fuel.core.FuelManager
import io.kotlintest.specs.StringSpec
import io.mockk.coVerify
import io.mockk.mockk
import service.AbstractAPI

class AbstractAPIImpl : AbstractAPI()

class AbstractAPITest : StringSpec() {

    init {

        "An implementation of abstractAPI shouldn't do an http if the result is cached" {

            FuelManager.instance.client = mockk()

            val api = AbstractAPIImpl()

            api.getCall("http://localhost:8000/api/myapi")
            api.getCall("http://localhost:8000/api/myapi")
            api.getCall("http://localhost:8000/api/myapi")

            coVerify(exactly = 1) { FuelManager.instance.client.awaitRequest(any()) }

            api.getCall("http://localhost:8000/api/myapi2")

            coVerify(exactly = 2) { FuelManager.instance.client.awaitRequest(any()) }

        }

    }

}