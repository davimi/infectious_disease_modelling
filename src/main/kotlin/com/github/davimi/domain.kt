package com.github.davimi

/**
 * The number of individuals not yet infected with the disease at time t, or those susceptible to the disease
 */
data class Susceptible(val t: Int, val amount: Long)

/**
 * The number of individuals who have been infected with the disease and are capable of spreading the disease to those in the susceptible category
 */
data class Infected(val t: Int, val amount: Long)

/**
 * Those individuals who have been infected and then recovered from the disease
 */
data class Recovered(val t: Int, val amount: Long)

/**
 * Individuals who died due to the disease
 */
data class Deceased(val t: Int, val amount: Long)

/**
 * The total amount of the population
 */
data class Population(val amount: Long)

/**
 * The State of the population at a given t
 */
data class State(
    val t: Int,
    val s: Susceptible,
    val i: Infected,
    val r: Recovered,
    val infectionRate: Double,
    val mortalityRate: Double,
    val d: Deceased
)