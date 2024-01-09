import kotlinx.serialization.Serializable

@Serializable
data class MRDataResponse(
    val MRData: MRData
)

@Serializable
data class MRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    val DriverTable: DriverTable
)

@Serializable
data class DriverTable(
    val season: String,
    val Drivers: List<Driver>
)

@Serializable
data class Driver(
    val driverId: String,
    val permanentNumber: String,
    val code: String,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String
)
