package com.github.davimi

fun main(args: Array<String>) {

    val simulation = Simulation(Population(10000), 0.4, 0.02, 3, 200)
    simulation.run()

    val results: List<State> = simulation.simulationResults.orEmpty()

    Visualization.plotSimulationResult(results)

}