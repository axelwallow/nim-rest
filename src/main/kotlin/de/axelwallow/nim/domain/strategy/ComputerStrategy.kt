package de.axelwallow.nim.domain.strategy

interface ComputerStrategy {
    fun pick(matches: Int): Int
}