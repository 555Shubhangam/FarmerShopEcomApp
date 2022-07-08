package com.farmershop.ui.search

import com.farmershop.R
import com.farmershop.appSDK.BaseActivityUser
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.search.*

class Search : BaseActivityUser(), OnChartValueSelectedListener {
    lateinit var weekTwoSales:LineDataSet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        setUpLineChart()
        setDataToLineChart()
    }
    private fun setUpLineChart() {
        with(lineChart) {
            setOnChartValueSelectedListener(this@Search)
            axisRight.isEnabled = false
            animateX(1200, Easing.EaseInSine)

            description.isEnabled = false

            //for x-axis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.granularity = 1F
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.textColor = ContextCompat.getColor(this@Search, R.color.orange)
            xAxis.axisLineColor = ContextCompat.getColor(this@Search, R.color.orange)

            //for y-axis
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.axisLineColor = ContextCompat.getColor(this@Search, R.color.orange)
            axisLeft.textColor = ContextCompat.getColor(this@Search, R.color.orange)

            extraRightOffset = 30f

            legend.isEnabled = true
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.form = Legend.LegendForm.LINE
        }
    }
    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        private var items = arrayListOf("2015", "2016", "2017", "2018", "2019","2020","2021")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }
    private fun week1(): ArrayList<Entry> {
        val sales = ArrayList<Entry>()
        sales.add(Entry(0f, 500f))
        sales.add(Entry(1f, 300f))
        sales.add(Entry(2f, 200f))
        sales.add(Entry(3f, 150f))
        sales.add(Entry(4f, 456f))
        sales.add(Entry(5f, 40f))
        sales.add(Entry(6f, 50f))
        return sales
    }

    private fun week2(): ArrayList<Entry> {
        val sales = ArrayList<Entry>()
        sales.add(Entry(0f, 444f))
        sales.add(Entry(1f, 666f))
        sales.add(Entry(2f, 345f))
        sales.add(Entry(3f, 245f))
        sales.add(Entry(4f, 30f))
        sales.add(Entry(5f, 74f))
        sales.add(Entry(6f, 190f))
        return sales
    }

    private fun week3(): ArrayList<Entry> {
        val sales = ArrayList<Entry>()
        sales.add(Entry(0f, 335f))
        sales.add(Entry(1f, 112f))
        sales.add(Entry(2f, 260f))
        sales.add(Entry(3f, 170f))
        sales.add(Entry(4f, 150f))
        sales.add(Entry(5f, 250f))
        sales.add(Entry(6f, 380f))
        return sales
    }
    private fun setDataToLineChart() {

        val weekOneSales = LineDataSet(week1(), "Week 1")
        weekOneSales.lineWidth = 3f
        weekOneSales.valueTextSize = 15f
        weekOneSales.mode = LineDataSet.Mode.CUBIC_BEZIER
        weekOneSales.color = ContextCompat.getColor(this, R.color.red)
        weekOneSales.valueTextColor = ContextCompat.getColor(this, R.color.red)
        //weekOneSales.enableDashedLine(20F, 10F, 0F)

        weekTwoSales = LineDataSet(week2(), "Week 2")
        weekTwoSales.lineWidth = 3f
        weekTwoSales.valueTextSize = 15f
        weekTwoSales.mode = LineDataSet.Mode.CUBIC_BEZIER
        weekTwoSales.color = ContextCompat.getColor(this, R.color.blue)
        weekTwoSales.valueTextColor = ContextCompat.getColor(this, R.color.blue)
        weekTwoSales.setDrawValues(true)
        //weekTwoSales.enableDashedLine(20F, 10F, 0F)

        val weekThreeSales = LineDataSet(week3(), "Week 3")
        weekThreeSales.lineWidth = 3f
        weekThreeSales.valueTextSize = 15f
        weekThreeSales.mode = LineDataSet.Mode.CUBIC_BEZIER
        weekThreeSales.color = ContextCompat.getColor(this, R.color.green)
        weekThreeSales.valueTextColor = ContextCompat.getColor(this, R.color.green)
        //weekTwoSales.enableDashedLine(20F, 10F, 0F)


        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weekOneSales)
        dataSet.add(weekTwoSales)
        dataSet.add(weekThreeSales)

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.d("DSdsdsdsd",e.toString())
    }

    override fun onNothingSelected() {

    }
}