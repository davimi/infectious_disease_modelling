package com.github.davimi

/**
 * represents the number of individuals not yet infected with the disease at time t, or those susceptible to the disease
 */
data class Susceptible(val t: Int, val amount: Long)

/**
 * denotes the number of individuals who have been infected with the disease and are capable of spreading the disease to those in the susceptible category
 */
data class Infected(val t: Int, val amount: Long)

/**
 * those individuals who have been infected and then removed from the disease, either due to immunization or due to death
 */
data class Removed(val t: Int, val amount: Long)


data class Deceased(val t: Int, val amount: Long)

/**
 * The total amount of the population
 */
data class Population(val amount: Long)

/**
 * The disease rate, which is considered the contact or infection rate of the disease
 */
data class Beta(val value: Double)

data class State(
    val t: Int,
    val s: Susceptible,
    val i: Infected,
    val r: Removed,
    val b: Double,
    val delta: Double,
    val d: Deceased
)