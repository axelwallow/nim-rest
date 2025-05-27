package de.axelwallow.nim.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource

class GameModeTest {
    @ParameterizedTest
    @ValueSource(strings = ["random", "RaNdOm", "RANDOM"])
    fun `returns RANDOM for any case`(input: String) {
        assertEquals(GameMode.RANDOM, GameMode.find(input))
    }

    @ParameterizedTest
    @ValueSource(strings = ["smart", "SMART", "SmArT"])
    fun `returns SMART for any case`(input: String) {
        assertEquals(GameMode.SMART, GameMode.find(input))
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = ["foo", "123"])
    fun `returns RANDOM for invalid or null`(input: String?) {
        assertEquals(GameMode.RANDOM, GameMode.find(input))
    }
}