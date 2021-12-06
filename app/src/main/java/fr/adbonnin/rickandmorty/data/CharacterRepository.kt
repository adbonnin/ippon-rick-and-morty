package fr.adbonnin.rickandmorty.data

import fr.adbonnin.rickandmorty.api.ApiRequest
import fr.adbonnin.rickandmorty.api.Page
import fr.adbonnin.rickandmorty.api.RickAndMortyApiService
import fr.adbonnin.rickandmorty.model.Character

class CharacterRepository {

    private val apiService = RickAndMortyApiService.create()

    suspend fun findByPage(page: Int): Page<Character> {
        val query = """
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

        val response = apiService.query(ApiRequest(query))
        return response.data?.characters ?: Page()
    }

    suspend fun findById(id: Int): Character? {
        val query = """
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

        val response = apiService.query(ApiRequest(query))
        return response.data?.character
    }
}