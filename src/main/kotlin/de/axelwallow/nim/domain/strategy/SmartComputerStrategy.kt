package de.axelwallow.nim.domain.strategy

class SmartComputerStrategy : ComputerStrategy {
    override fun pick(matches: Int) = if (matches % 4 == 0) 3 else (matches % 4) - 1
}