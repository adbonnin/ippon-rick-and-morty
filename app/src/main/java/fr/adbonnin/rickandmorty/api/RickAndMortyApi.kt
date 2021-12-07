package fr.adbonnin.rickandmorty.api

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class RickAndMortyApi {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/graphql"

        fun create(): ApolloClient {
            val level = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(level)
                .build()

            return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(client)
                .build()
        }
    }
}