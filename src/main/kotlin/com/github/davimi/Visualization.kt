package com.github.davimi

import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries

import com.github.davimi.Utils.renderWithPrefix


object Visualization {

    fun plotModelResult(simulationResults: Collection<State>, beta: Double? = null, gamma: Double? = null) {

        val title = "SRIModel" + beta.renderWithPrefix(" - beta: ") + gamma.renderWithPrefix(" - gamma: ")
        val chart: XYChart = XYChartBuilder().width(900).height(600).title(title).xAxisTitle("time").yAxisTitle("amount of individuals").build()

        chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line

        chart.addSeries("Removed", simulationResults.map { it.r.amount })
        chart.addSeries("Susceptible", simulationResults.map { it.s.amount })
        chart.addSeries("Infected", simulationResults.map { it.i.amount })

        SwingWrapper(chart).displayChart();

    }



}