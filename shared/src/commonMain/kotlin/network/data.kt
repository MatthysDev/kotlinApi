import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.body

class ApiClient {
    private val httpClient = HttpClient {
        // Configure your client
    }

    suspend fun getDriversByYear(): MRData {
        val response = httpClient.get("http://ergast.com/api/f1/2023/drivers.json")
        return response.body()
    }
}
