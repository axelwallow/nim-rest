package de.axelwallow.nim.application

import de.axelwallow.nim.domain.GameMode
import de.axelwallow.nim.domain.strategy.RandomComputerStrategy
import de.axelwallow.nim.domain.strategy.SmartComputerStrategy
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource

class ComputerStrategyFactoryTest {

    private val factory = ComputerStrategyFactory()

    @Test
    fun `returns SmartComputerStrategy for SMART mode`() {
        val strategy = factory.getStrategy(GameMode.SMART)

        assertTrue(strategy is SmartComputerStrategy)
        assertSame(strategy, factory.getStrategy(GameMode.SMART))
    }

    @Test
    fun `returns RandomComputerStrategy for RANDOM mode`() {
        val strategy = factory.getStrategy(GameMode.RANDOM)

        assertTrue(strategy is RandomComputerStrategy)
        assertSame(strategy, factory.getStrategy(GameMode.RANDOM))
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = ["UNKNOWN", "FOO"])
    fun `falls back to RandomComputerStrategy for unknown modes`(value: String) {
        val strategy = GameMode.find(value)
            .let { factory.getStrategy(it) }

        assertTrue(strategy is RandomComputerStrategy)
    }
}