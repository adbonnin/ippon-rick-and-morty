package fr.adbonnin.rickandmorty.model

import com.google.gson.annotations.SerializedName

private const val STATUS_ALICE = "Alive"
private const val STATUS_DEAD = "Dead"
private const val STATUS_UNKNOWN = "unknown"

private const val GENDER_FEMALE = "Female"
private const val GENDER_MALE = "Male"
private const val GENDER_GENDERLESS = "Genderless"
private const val GENDER_UNKNOWN = "unknown"

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