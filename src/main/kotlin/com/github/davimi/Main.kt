package com.github.davimi

fun main(args: Array<String>) {

    val infectionRate = 0.4
    val recoveryRate = 0.04
    val mortalityRate = 0.005
    val initiallyInfected = 3

    val simulation = SRIDModel(Population(10000), infectionRate, recoveryRate, mortalityRate, initiallyInfected, 150)

    val results: List<State> = simulation.run().toList()

    Visualization.plotModelResult(results, false, infectionRate, recoveryRate, mortalityRate)

}