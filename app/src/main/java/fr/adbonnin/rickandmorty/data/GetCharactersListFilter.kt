package fr.adbonnin.rickandmorty.data

data class GetCharactersListFilter(
    val gender: Gender = Gender.ALL,
    val status: Status = Status.ALL,
) {
    enum class Gender(val value: String? = null) {
        ALL(null),
        MALE("Male"),
        FEMALE("Female"),
        UNKNOWN("unknown");
    }

    enum class Status(val value: String? = null) {
        ALL(null),
        ALIVE("Alive"),
        DEAD("Dead"),
        UNKNOWN("unknown");
    }
}