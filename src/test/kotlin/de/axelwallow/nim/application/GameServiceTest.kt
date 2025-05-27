package de.axelwallow.nim.application

import de.axelwallow.nim.domain.GameMode
import de.axelwallow.nim.domain.GameStatus
import de.axelwallow.nim.domain.strategy.ComputerStrategy
import de.axelwallow.nim.infrastructure.InMemoryGameStateStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GameServiceTest {

    private lateinit var store: InMemoryGameStateStore
    private lateinit var factory: ComputerStrategyFactory
    private lateinit var service: GameService

    private val dummyCpu: ComputerStrategy = object : ComputerStrategy {
        override fun pick(matches: Int) = 1
    }

    @BeforeEach
    fun setUp() {
        store = InMemoryGameStateStore()

        factory = object : ComputerStrategyFactory() {
            override fun getStrategy(mode: GameMode) = dummyCpu
        }

        service = GameService(factory, store)
    }

    @Test
    fun `createGame initialises and persists state`() {
        val game = service.createGame(GameMode.RANDOM)

        assertEquals(13, game.matches)
        assertEquals(GameStatus.RUNNING, game.status)
        assertEquals(GameMode.RANDOM, game.mode)

        assertSame(game, store.load(game.id))
    }

    @Test
    fun `player loses when taking last match`() {
        val game = service.createGame(GameMode.RANDOM)
            .also { it.matches = 1 }
            .also { store.store(it) }

        service.makeMove(game.id, 1)
        val updated = store.load(game.id)

        assertEquals(GameStatus.PLAYER_LOST, updated.status)
        assertEquals(0, updated.matches)
    }

    @Test
    fun `computer loses when its move empties pile`() {
        val game = service.createGame(GameMode.RANDOM)
            .also { it.matches = 2 }
            .also { store.store(it) }

        service.makeMove(game.id, 1)
        val updated = store.load(game.id)

        assertEquals(GameStatus.COMPUTER_LOST, updated.status)
        assertEquals(0, updated.matches)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 4, -1])
    fun `invalid move outside 1-3 throws exception`(taken: Int) {
        val game = service.createGame(GameMode.RANDOM)

        assertThrows(IllegalArgumentException::class.java) {
            service.makeMove(game.id, taken)
        }
    }

    @Test
    fun `cannot take more matches than remain`() {
        val game = service.createGame(GameMode.RANDOM)
            .also { it.matches = 2 }
            .also { store.store(it) }

        assertThrows(IllegalArgumentException::class.java) {
            service.makeMove(game.id, 3)
        }
    }
}