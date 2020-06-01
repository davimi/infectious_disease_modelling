package com.github.davimi

import com.github.davimi.Utils.renderWithPrefix
import org.knowm.xchart.*


object Visualization {

    fun plotModelResult(simulationResults: Collection<State>, saveImage: Boolean = false, infectionRate: Double? = null, recoveryRate: Double? = null, mortalityRate: Double? = null) {

        val title = "SRIDModel" +
                infectionRate.renderWithPrefix(" - infection rate: ") +
                recoveryRate.renderWithPrefix(" - recovery rate: ") +
                mortalityRate.renderWithPrefix(" - mortality rate: ")
        val chart: XYChart = XYChartBuilder().width(900).height(600).title(title).xAxisTitle("time").yAxisTitle("amount of individuals").build()

        chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line

        chart.addSeries("Recovered", simulationResults.map { it.r.amount })
        chart.addSeries("Susceptible", simulationResults.map { it.s.amount })
        chart.addSeries("Infected", simulationResults.map { it.i.amount })
        chart.addSeries("Deceased", simulationResults.map { it.d.amount })

        SwingWrapper(chart).displayChart();

        if (saveImage) {
            saveChart(chart, title)
        }

    }

    private fun saveChart(chart: XYChart, name: String) {
        BitmapEncoder.saveBitmap(chart, "./$name.jpg", BitmapEncoder.BitmapFormat.JPG);
    }


}