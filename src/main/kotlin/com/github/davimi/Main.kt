package com.github.davimi

fun main(args: Array<String>) {

    val beta = 0.4
    val gamma = 0.02
    val simulation = SRIModel(Population(10000), beta, gamma, 3, 200)
    simulation.run()

    val results: List<State> = simulation.simulationResults.orEmpty()

    Visualization.plotModelResult(results, beta, gamma)

}