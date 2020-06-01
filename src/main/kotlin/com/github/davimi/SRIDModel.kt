package com.github.davimi

import kotlin.math.roundToLong

/**
 * Simulates an infectious disease over @param maxTimeSteps of population @param N.
 * The virus starts with an initiallyInfected amount of people infected.
 * Individuals go from an susceptible to an infected to an recovered or deceased state: S -> I -> (R or D).
 * N = S + I + R + D. N is fixed throughout the simulation. Once recovered or deceased, the individual cannot be susceptible again.
 */
class SRIDModel(val N: Population, private val infectionRate: Double, private val recoveryRate: Double, private val mortalityRate: Double, private val initiallyInfected: Int, private val maxTimeSteps: Int) : Model {

    private val t0 = 0
    var simulationResults: List<State>? = null

    fun calcNewSusceptible(s: Susceptible, i: Infected): Susceptible {
        val change: Long = (-1 * ((infectionRate * s.amount * i.amount) / N.amount)).roundToLong()
        val newSusceptible = Susceptible(s.t + 1, s.amount + change)
        return newSusceptible
    }

    fun calcNewInfected(s: Susceptible, i: Infected): Infected {
        val change: Long = (((infectionRate * s.amount * i.amount) / N.amount) - (recoveryRate * i.amount)).roundToLong() - (mortalityRate * i.amount).roundToLong()
        val newInfected = Infected(i.t + 1, i.amount + change)
        return newInfected
    }

    fun calcNewRemoved(r: Recovered, i: Infected): Recovered {
        val change = (recoveryRate * i.amount).roundToLong()
        val newRemoved = Recovered(r.t + 1, r.amount + change)
        return newRemoved
    }

    fun calcNewDeseased(d: Deceased, i: Infected): Deceased {
        val change = (mortalityRate * i.amount).roundToLong()
        val newDeceased = Deceased(d.t + 1, d.amount + change)
        return newDeceased
    }

    fun step(state: State): State {

        val newT = state.t + 1
        val newInfected = calcNewInfected(state.s, state.i)
        val newRemoved = calcNewRemoved(state.r, state.i)
        val newSusceptible = calcNewSusceptible(state.s, state.i)
        val newDeceased = calcNewDeseased(state.d, state.i)

        return State(newT, newSusceptible, newInfected, newRemoved, state.infectionRate, state.mortalityRate, newDeceased)
    }

    override fun run(): Collection<State> {
        val initialState = State(
            t0,
            Susceptible(t0, N.amount),
            Infected(t0, initiallyInfected.toLong()),
            Recovered(t0, 0),
            infectionRate,
            mortalityRate,
            Deceased(t0, 0)
        )

        val states: MutableList<State> = mutableListOf()
        var currentState = initialState

        for (i in 0 until maxTimeSteps) {
            val newCurrentState = step(currentState)
            states.add(newCurrentState)
            currentState = newCurrentState
            //println("Time ${currentState.t}: Infected: ${currentState.i.amount}, Removed: ${currentState.r.amount}")
        }

        this.simulationResults = states.toList()

        return this.simulationResults.orEmpty()
    }
}