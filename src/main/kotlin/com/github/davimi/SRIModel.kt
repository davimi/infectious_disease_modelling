package com.github.davimi

import kotlin.math.roundToLong

/**
 * Simulates an infectious disease over @param maxTimeSteps of population @param N. Beta is the infection rate of the disease, gamma the mean removal rate (death and/or cured).
 * The virus starts with an initiallyInfected amount of people infected.
 * Individuals go from an susceptible to an infected to an removed state: S -> I -> R.
 * N = S + I + R. N is fixed throughout the simulation.
 */
class SRIModel(val N: Population, private val beta: Double, private val gamma: Double, val delta: Double, private val initiallyInfected: Int, private val maxTimeSteps: Int) : Model {

    private val t0 = 0
    var simulationResults: List<State>? = null

    fun calcNewSusceptible(s: Susceptible, i: Infected): Susceptible {
        val change: Long = (-1 * ((beta * s.amount * i.amount) / N.amount)).roundToLong()
        val newSusceptible = Susceptible(s.t + 1, s.amount + change)
        return newSusceptible
    }

    fun calcNewInfected(s: Susceptible, i: Infected): Infected {
        val change: Long = (((beta * s.amount * i.amount) / N.amount) - (gamma * i.amount)).roundToLong() - (delta * i.amount).roundToLong()
        val newInfected = Infected(i.t + 1, i.amount + change)
        return newInfected
    }

    fun calcNewRemoved(r: Removed, i: Infected): Removed {
        val change = (gamma * i.amount).roundToLong()
        val newRemoved = Removed(r.t + 1, r.amount + change)
        return newRemoved
    }

    fun calcNewDeseased(d: Deceased, i: Infected): Deceased {
        val change = (delta * i.amount).roundToLong()
        val newDeceased = Deceased(d.t + 1, d.amount + change)
        return newDeceased
    }

    fun step(state: State): State {

        val newT = state.t + 1
        val newInfected = calcNewInfected(state.s, state.i)
        val newRemoved = calcNewRemoved(state.r, state.i)
        val newSusceptible = calcNewSusceptible(state.s, state.i)
        val newDeceased = calcNewDeseased(state.d, state.i)

        return State(newT, newSusceptible, newInfected, newRemoved, state.b, state.delta, newDeceased)
    }

    override fun run() {
        val initialState = State(
            t0,
            Susceptible(t0, N.amount),
            Infected(t0, initiallyInfected.toLong()),
            Removed(t0, 0),
            beta,
            delta,
            Deceased(t0, 0)
        )

        val states: MutableList<State> = mutableListOf()
        var currentState = initialState

        for (i in 0 until maxTimeSteps) {
            val newCurrentState = step(currentState)
            states.add(newCurrentState)
            currentState = newCurrentState
            println("Time ${currentState.t}: Infected: ${currentState.i.amount}, Removed: ${currentState.r.amount}")
        }

        this.simulationResults = states.toList()
    }
}