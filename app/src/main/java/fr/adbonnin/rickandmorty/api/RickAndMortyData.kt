package fr.adbonnin.rickandmorty.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

private const val STATUS_ALICE = "Alive"
private const val STATUS_DEAD = "Dead"
private const val STATUS_UNKNOWN = "unknown"

private const val GENDER_FEMALE = "Female"
private const val GENDER_MALE = "Male"
private const val GENDER_GENDERLESS = "Genderless"
private const val GENDER_UNKNOWN = "unknown"

class RickAndMortyData {
    companion object {
        fun buildGson(): Gson {
            return GsonBuilder().create()
        }
    }
}

data class GraphQLRequest(
    val operationName: String? = null,
    val query: String,
    val variables: Map<String, Any?> = emptyMap(),
)

data class GraphQLResponse(
    val data: GraphQLData?,
)

data class GraphQLData(
    val character: Character?,
    val characters: Characters?,
)

data class ListInfo(
    val count: Int?
)

data class Characters(
    val info: ListInfo?,
    val results: List<Character>?,
)

data class Character(
    val id: Int? = null,
    val name: String? = null,
    val status: Status? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: Gender? = null,
    val origin: Location? = null,
    val location: Location? = null,
    val image: String? = null,
    val episode: List<Episode>? = null,
    val created: String? = null,
) {
    enum class Status(val label: String) {
        @SerializedName(STATUS_ALICE)
        ALIVE(STATUS_ALICE),

        @SerializedName(STATUS_DEAD)
        DEAD(STATUS_DEAD),

        @SerializedName(STATUS_UNKNOWN)
        UNKNOWN(STATUS_UNKNOWN),
    }

    enum class Gender(val label: String) {
        @SerializedName(GENDER_FEMALE)
        FEMALE(GENDER_FEMALE),

        @SerializedName(GENDER_MALE)
        MALE(GENDER_MALE),

        @SerializedName(GENDER_GENDERLESS)
        GENDERLESS(GENDER_GENDERLESS),

        @SerializedName(GENDER_UNKNOWN)
        UNKNOWN(GENDER_UNKNOWN),
    }
}

data class Location(
    val id: Int?,
    val name: String?,
    val type: String?,
    val dimension: String?,
    val residents: List<Character>?,
    val created: String?,
)

data class Episode(
    val id: Int?,
    val name: String?,
    @SerializedName("air_date") val airDate: String?,
    val episode: String?,
    val characters: List<String>?,
    val created: String?,
)