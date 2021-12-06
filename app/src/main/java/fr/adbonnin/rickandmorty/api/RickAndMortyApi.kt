package fr.adbonnin.rickandmorty.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RickAndMortyApi {

    @POST("/graphql")
    fun query(@Body request: GraphQLRequest): Call<GraphQLResponse>
}