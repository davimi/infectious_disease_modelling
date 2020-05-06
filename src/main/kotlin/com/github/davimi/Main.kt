package com.github.davimi

import kotlin.math.roundToLong

fun main(args: Array<String>) {
    val simulation = Simulation(Population(1000000), 0.4, 0.04, 3, 1000)
    simulation.run()
}

/**
 * Simulates an infectious disease over @param maxTimeSteps of population @param N. Beta is the infection rate of the disease, gamma the mean removal rate (death and/or cured).
 * The virus starts with an initiallyInfected amount of people infected.
 * Individuals go from an susceptible to an infected to an removed state: S -> I -> R.
 * N = S + I + R. N is fixed throughout the simulation.
 */
class Simulation(val N: Population, val beta: Double, val gamma: Double, val initiallyInfected: Int, val maxTimeSteps: Int) {

    private val t0 = 0

    fun calcNewSusceptible(s: Susceptible, i: Infected): Susceptible {
        val change: Long = (-1 * ((beta * s.amount * i.amount) / N.amount)).roundToLong()
        val newSusceptible = Susceptible(s.t, s.amount + change)
        return newSusceptible
    }

    fun calcNewInfected(s: Susceptible, i: Infected): Infected {
        val change: Long = (((beta * s.amount * i.amount) / N.amount) - (gamma * i.amount)).roundToLong()
        val newInfected = Infected(i.t, i.amount + change)
        return newInfected
    }

    fun calcNewRemoved(r: Removed, i: Infected): Removed {
        val change = (gamma * i.amount).roundToLong()
        val newRemoved = Removed(r.t, r.amount + change)
        return newRemoved
    }

    fun step(state: State): State {

        val newT = state.t + 1
        val newInfected = calcNewInfected(state.s, state.i)
        val newRemoved = calcNewRemoved(state.r, state.i)
        val newSusceptible = calcNewSusceptible(state.s, state.i)

        return State(newT, newSusceptible, newInfected, newRemoved, state.b)
    }

    fun run() {
        val initialState = State(t0, Susceptible(t0, N.amount), Infected(t0, initiallyInfected.toLong()), Removed(t0, 0), Beta(beta))

        val states: MutableList<State> = mutableListOf()
        var currentState = initialState

        for (i in 0 until maxTimeSteps) {
            val newCurrentState = step(currentState)
            states.add(newCurrentState)
            currentState = newCurrentState
            println("Time ${currentState.t}: Infected: ${currentState.i.amount}, Removed: ${currentState.r.amount}")
        }
    }
}