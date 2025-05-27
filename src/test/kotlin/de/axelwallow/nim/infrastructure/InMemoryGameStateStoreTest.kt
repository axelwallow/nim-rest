package de.axelwallow.nim.infrastructure

import de.axelwallow.nim.domain.GameMode
import de.axelwallow.nim.domain.GameState
import de.axelwallow.nim.domain.GameStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class InMemoryGameStateStoreTest {

    private val store = InMemoryGameStateStore()

    @Test
    fun `stores and loads same instance`() {
        val game = GameState(
            id = UUID.randomUUID(),
            matches = 13,
            status = GameStatus.RUNNING,
            mode = GameMode.RANDOM
        ).also { store.store(it) }

        val loaded = store.load(game.id)

        assertSame(game, loaded)
    }

    @Test
    fun `loading unknown id throws`() {
        val unknownId = UUID.randomUUID()
        val ex = assertThrows(IllegalArgumentException::class.java) {
            store.load(unknownId)
        }

        assertTrue(ex.message!!.contains(unknownId.toString()))
    }
}