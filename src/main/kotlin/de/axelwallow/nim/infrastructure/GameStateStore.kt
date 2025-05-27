package de.axelwallow.nim.infrastructure

import de.axelwallow.nim.domain.GameState
import java.util.*

interface GameStateStore {
    fun store(gameState: GameState): GameState
    fun load(id: UUID): GameState
}