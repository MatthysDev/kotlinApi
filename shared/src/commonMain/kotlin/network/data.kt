import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                // Configuration de sérialisation si nécessaire
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getDriversByYear(year: String): MRDataResponse {
        val response = httpClient.get("http://ergast.com/api/f1/$year/drivers.json")
        // Deserialization of the response into an MRData object
        return response.body()
    }
}
