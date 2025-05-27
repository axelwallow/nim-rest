package de.axelwallow.nim.domain

enum class GameMode {
    RANDOM,
    SMART;

    companion object {
        fun find(value: String?): GameMode =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: RANDOM
    }
}