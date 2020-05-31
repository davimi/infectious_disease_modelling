package com.github.davimi

fun main(args: Array<String>) {

    val beta = 0.4
    val gamma = 0.1
    val delta = 0.03
    val simulation = SRIModel(Population(10000), beta, gamma, delta, 3, 150)
    simulation.run()

    val results: List<State> = simulation.simulationResults.orEmpty()

    Visualization.plotModelResult(results, beta, gamma)

}