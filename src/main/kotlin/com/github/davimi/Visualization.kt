package com.github.davimi

import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries


object Visualization {

    fun plotSimulationResult(simulationResults: Collection<State>) {
        val chart: XYChart = XYChartBuilder().width(900).height(600).title("Simulation").xAxisTitle("time").yAxisTitle("amount of individuals").build()

        chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line

        chart.addSeries("Removed", simulationResults.map { it.r.amount })
        chart.addSeries("Susceptible", simulationResults.map { it.s.amount })
        chart.addSeries("Infected", simulationResults.map { it.i.amount })

        SwingWrapper(chart).displayChart();

    }



}