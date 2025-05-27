package de.axelwallow.nim.domain.strategy

class RandomComputerStrategy : ComputerStrategy {
    override fun pick(matches: Int) = (1..minOf(3, matches)).random()
}