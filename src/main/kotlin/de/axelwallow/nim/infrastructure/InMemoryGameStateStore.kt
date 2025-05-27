package de.axelwallow.nim.infrastructure

import de.axelwallow.nim.domain.GameState
import jakarta.enterprise.context.ApplicationScoped
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@ApplicationScoped
class InMemoryGameStateStore : GameStateStore {

    private val games = ConcurrentHashMap<UUID, GameState>()

    override fun store(gameState: GameState): GameState {
        games[gameState.id] = gameState
        return gameState
    }

    override fun load(id: UUID): GameState {
        val gameState = games[id]
        return gameState ?: throw IllegalArgumentException("GameState with id $id does not exist")
    }
}