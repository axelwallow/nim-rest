package de.axelwallow.nim.api.dto

import de.axelwallow.nim.domain.GameStatus
import java.util.*

data class MoveResponse(
    val id: UUID,
    val matches: Int,
    val status: GameStatus,
    val lastComputerMove: Int,
)