package com.github.davimi

fun main(args: Array<String>) {

    val infectionRate = 0.4
    val recoveryRate = 0.04
    val mortalityRate = 0.005
    val initiallyInfected = 3
    val simulation = SRIModel(Population(10000), infectionRate, recoveryRate, mortalityRate, initiallyInfected, 150)
    simulation.run()

    val results: List<State> = simulation.simulationResults.orEmpty()

    Visualization.plotModelResult(results, infectionRate, recoveryRate)

}