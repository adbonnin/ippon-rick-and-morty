package fr.adbonnin.rickandmorty.api

import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterTest {

    private val gson = RickAndMortyData.buildGson()

    @Test
    fun `Doit parser un personnage`() {
        // when:
        val character = parseCharacter(
            """
            {
              "id": "1",
              "name": "Rick Sanchez",
              "status": "Alive",
              "gender": "Male"
            }
            """
        )

        // then:
        assertEquals(character.id, "1")
        assertEquals(character.name, "Rick Sanchez")
        assertEquals(character.status, Character.Status.ALIVE)
        assertEquals(character.gender, Character.Gender.MALE)
    }

    @Test
    fun `Doit parser les status d'un personnage`() {
        // expect:
        assertEquals(parseCharacter("""{ "status": null }""").status, null)
        assertEquals(parseCharacter("""{ "status": "test" }""").status, null)
        assertEquals(parseCharacter("""{ "status": "Alive" }""").status, Character.Status.ALIVE)
        assertEquals(parseCharacter("""{ "status": "Dead" }""").status, Character.Status.DEAD)
        assertEquals(parseCharacter("""{ "status": "unknown" }""").status, Character.Status.UNKNOWN)
    }

    @Test
    fun `Doit parser le genres d'un personnage`() {
        // expect:
        assertEquals(parseCharacter("""{ "gender": null }""").gender, null)
        assertEquals(parseCharacter("""{ "gender": test }""").gender, null)
        assertEquals(parseCharacter("""{ "gender": "Female" }""").gender, Character.Gender.FEMALE)
        assertEquals(parseCharacter("""{ "gender": "Male" }""").gender, Character.Gender.MALE)
        assertEquals(parseCharacter("""{ "gender": "Genderless" }""").gender, Character.Gender.GENDERLESS)
        assertEquals(parseCharacter("""{ "gender": "unknown" }""").gender, Character.Gender.UNKNOWN)
    }

    private fun parseCharacter(json: String): Character {
        return gson.fromJson(json, Character::class.java)
    }
}