data class MRData(val DriverTable: DriverTable)
data class DriverTable(val Drivers: List<Driver>)
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
