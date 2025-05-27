package de.axelwallow.nim.application

import de.axelwallow.nim.domain.GameMode
import de.axelwallow.nim.domain.GameState
import de.axelwallow.nim.domain.GameStatus
import de.axelwallow.nim.infrastructure.GameStateStore
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

/**
 * Central application service that orchestrates a single Nim game life‑cycle.
 *
 * Responsibilities
 * * creates a new [GameState] with a UUID and persists it via [GameStateStore]
 * * validates and applies the **player move**
 * * delegates the **computer move** to the selected [ComputerStrategy]
 * * updates the game status (RUNNING / PLAYER_LOST / COMPUTER_LOST)
 *
 */
@ApplicationScoped
class GameService(
    private val strategyFactory: ComputerStrategyFactory,
    private val gameStateStore: GameStateStore,
) {
    fun createGame(mode: GameMode): GameState =
        GameState(id = UUID.randomUUID(), mode = mode)
            .also { gameStateStore.store(it) }

    fun getGame(id: UUID): GameState = gameStateStore.load(id)

    fun makeMove(id: UUID, taken: Int): GameState {
        val game = gameStateStore.load(id)
        makePlayerMove(game, taken)
        makeComputerMove(game)
        return game
    }

    private fun makePlayerMove(game: GameState, matchesTaken: Int) {
        validateMove(game, matchesTaken)
        game.matches -= matchesTaken
        if (game.matches == 0) {
            game.status = GameStatus.PLAYER_LOST
        }
    }

    private fun makeComputerMove(game: GameState) {
        if (game.status != GameStatus.RUNNING) return
        val matchesTaken = strategyFactory.getStrategy(game.mode).pick(game.matches)
        game.matches -= matchesTaken
        game.lastComputerMove = matchesTaken
        if (game.matches == 0) {
            game.status = GameStatus.COMPUTER_LOST
        }
    }

    private fun validateMove(gameState: GameState, taken: Int) {
        require(taken in 1..3) { "Move must be between 1 and 3" }
        require(gameState.status == GameStatus.RUNNING) { "Game already finished" }
        require(taken <= gameState.matches) { "Cannot take more matches than remain" }
    }
}
