package de.axelwallow.nim.api.dto

import de.axelwallow.nim.domain.GameStatus
import java.util.*

data class GameResponse(
    val id: UUID,
    val matches: Int,
    val status: GameStatus,
)