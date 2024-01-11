import kotlinx.serialization.Serializable

@Serializable
data class RacesDataResponse(
    val MRData: RacesMRData
)

@Serializable
data class RacesMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    val RaceTable: RaceTable
)

@Serializable
data class RaceTable(
    val season: String,
    val Races: List<Race>
)

@Serializable
data class Race(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    val Circuit: Circuit,
    val date: String
)

@Serializable
data class Circuit(
    val circuitId: String,
    val url: String,
    val circuitName: String,
    val Location: Location
)

@Serializable
data class Location(
    val lat: String,
    val long: String,
    val locality: String,
    val country: String
)

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

@Serializable
data class LastRaceResultsDataResponse(
    val MRData: LastRaceMRData
)

@Serializable
data class LastRaceMRData(
    val RaceTable: LastRaceTable
)

@Serializable
data class LastRaceTable(
    val Races: List<LastRace>
)

@Serializable
data class LastRace(
    val season: String,
    val round: String,
    val raceName: String,
    val Circuit: Circuit,
    val date: String,
    val Results: List<RaceResult>
)

@Serializable
data class RaceResult(
    val number: String,
    val position: String,
    val Driver: Driver,
    val Constructor: Constructor,
    val grid: String,
    val laps: String,
    val status: String,
    val Time: RaceTime?
)

@Serializable
data class RaceTime(
    val time: String
)



@Serializable
data class ConstructorsDataResponse(
    val MRData: ConstructorsMRData
)

@Serializable
data class ConstructorsMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    val ConstructorTable: ConstructorTable
)

@Serializable
data class ConstructorTable(
    val Constructors: List<Constructor>
)

@Serializable
data class Constructor(
    val constructorId: String,
    val url: String,
    val name: String,
    val nationality: String
)
