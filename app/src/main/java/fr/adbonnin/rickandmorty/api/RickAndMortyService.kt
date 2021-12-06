package fr.adbonnin.rickandmorty.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyService {

    interface ResponseHandler<T> {

        fun onSuccess(value: T)

        fun onFailure(t: Throwable)
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: RickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)

    fun findByPage(page:Int, handler: ResponseHandler<List<Character>>) {
        query(
            handler, { response -> response?.data?.characters?.results ?: emptyList() }, """
            query {
              characters(page: $page) {
                info {
                  count,
                  pages,
                  next,
                  prev
                }
                results {
                  id,
                  name
                }
              }
            }
            """
        )
    }

    fun findById(id: Int, handler: ResponseHandler<Character?>) {
        query(
            handler, { response -> response?.data?.character }, """
            query {
              character(id: $id) {
                id,
                name,
                status,
                species,
                type,
                gender,
                origin {
                  id,
                  name,
                  type,
                  dimension
                },
                location {
                  id,
                  name,
                  type,
                  dimension
                },
                image,
                episode {
                  id,
                  name,
                  air_date,
                  episode
                }
              },
            }
            """
        )
    }

    private fun <T> query(handler: ResponseHandler<T>, map: (GraphQLResponse?) -> T, query: String) {

        val callback = object : Callback<GraphQLResponse> {
            override fun onResponse(call: Call<GraphQLResponse>, response: Response<GraphQLResponse>?) {
                val body = response?.body()
                val value = map(body)
                handler.onSuccess(value)
            }

            override fun onFailure(call: Call<GraphQLResponse>, t: Throwable) {
                handler.onFailure(t)
            }
        }

        val call = api.query(GraphQLRequest(query = query))
        call.enqueue(callback)
    }
}