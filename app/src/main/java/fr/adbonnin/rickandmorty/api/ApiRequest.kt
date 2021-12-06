package fr.adbonnin.rickandmorty.api

data class ApiRequest(
    val query: String,
    val operationName: String? = null,
    val variables: Map<String, Any?> = emptyMap(),
)