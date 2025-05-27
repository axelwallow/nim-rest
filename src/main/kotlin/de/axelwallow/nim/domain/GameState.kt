package de.axelwallow.nim.domain

import java.util.*

data class GameState(
    val id: UUID,
    val mode: GameMode,

    var matches: Int = 13,
    var lastComputerMove: Int = 0,
    var status: GameStatus = GameStatus.RUNNING,
)
