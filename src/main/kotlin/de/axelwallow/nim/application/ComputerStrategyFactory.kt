package de.axelwallow.nim.application

import de.axelwallow.nim.domain.GameMode
import de.axelwallow.nim.domain.strategy.ComputerStrategy
import de.axelwallow.nim.domain.strategy.RandomComputerStrategy
import de.axelwallow.nim.domain.strategy.SmartComputerStrategy
import jakarta.enterprise.context.ApplicationScoped

/**
 * Central registry that selects the _ComputerStrategy_ for a given [GameMode].
 *
 * * Keys are the game modes (SMART, RANDOM).
 * * Values are singleton strategy instances shared across all games.
 *
 * To add a new strategy just:
 *  1. Implement [de.axelwallow.nim.domain.strategy.ComputerStrategy].
 *  2. Add a line to the [strategies] map.
 */
@ApplicationScoped
class ComputerStrategyFactory {

    private val strategies = mapOf(
        GameMode.SMART to SmartComputerStrategy(),
        GameMode.RANDOM to RandomComputerStrategy()
    )

    fun getStrategy(mode: GameMode): ComputerStrategy {
        return strategies[mode] ?: throw IllegalArgumentException("Strategy with mode $mode not found")
    }
}