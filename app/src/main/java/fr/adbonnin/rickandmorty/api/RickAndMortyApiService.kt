package fr.adbonnin.rickandmorty.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RickAndMortyApiService {

    @POST("/graphql")
    suspend fun query(@Body request: ApiRequest): ApiResponse

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/"

        fun create(): RickAndMortyApiService {
            val level = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(level)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RickAndMortyApiService::class.java)
        }
    }
}