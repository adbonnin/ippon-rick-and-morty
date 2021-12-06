package fr.adbonnin.rickandmorty.api

data class Page<T>(
    val info: Info = Info(),
    val results: List<T> = emptyList(),
) {
    data class Info(
        val count: Int? = null,
        val pages: Int? = null,
        val next: Int? = null,
        val prev: Int? = null,
    )
}