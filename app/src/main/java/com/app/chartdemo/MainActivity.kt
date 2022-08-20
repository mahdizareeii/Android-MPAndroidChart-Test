package com.app.chartdemo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.EntryXComparator
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private var chart: LineChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chart = findViewById(R.id.chart)

        val markerView = MyMarkerView(this@MainActivity, R.layout.marker)
        markerView.chartView = chart
        chart?.marker = markerView

        val maxY = 100f
        val minY = 0f

        val maxX = 31f
        val minX = 0f
        chart?.apply {
            setOnChartValueSelectedListener(this@MainActivity)
            isDragEnabled = true
            setPinchZoom(true)
            setTouchEnabled(true)
            setScaleEnabled(true)

//            animateY(2500)
            description.isEnabled = false
            setDrawGridBackground(false)

            legend.form = LegendForm.EMPTY
            legend.textSize = 11f
            legend.textColor = Color.parseColor("#737779")
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)
            //legend.yOffset = 11f

            val xAxis = xAxis
            xAxis.axisLineColor = Color.parseColor("#E7E8E9")
            //xAxis.typeface = tfLight
            //to hide bottom text
            xAxis.axisLineWidth = 1f
            xAxis.textSize = 0f
            xAxis.textColor = Color.TRANSPARENT
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.axisMinimum = minX
            xAxis.axisMaximum = maxX

            val leftAxis = axisLeft
            //leftAxis.typeface = tfLight
            leftAxis.textColor = Color.parseColor("#737779")
            leftAxis.setDrawGridLines(false)
            leftAxis.setDrawAxisLine(false)
            leftAxis.axisMaximum = maxY
            leftAxis.axisMinimum = minY
            leftAxis.isGranularityEnabled = true

            val rightAxis = axisRight
            rightAxis.isEnabled = false
        }
        setData(30, 60f)
    }

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<Entry>()
        for (i in 0 until count) {
            val xVal = (i).toFloat()
            val yVal = (Random.nextInt(0, range.toInt())).toFloat()
            entries.add(Entry(xVal, yVal))
        }

        // sort by x-value
        Collections.sort(entries, EntryXComparator())

        // create a dataset and give it a type
        val set1 = LineDataSet(entries, "")
        set1.color = Color.parseColor("#E7E8E9")
        set1.setDrawHorizontalHighlightIndicator(false)
        set1.setDrawVerticalHighlightIndicator(false)
        set1.lineWidth = 2f
        set1.setCircleColor(Color.parseColor("#131B1F"))
        set1.circleRadius = 2f
        set1.setDrawValues(false)
        set1.setDrawCircleHole(false)

        // create a data object with the data sets
        val data = LineData(set1)

        // set data
        chart?.data = data
        chart?.notifyDataSetChanged()
        //chart?.invalidate()
    }

    var beforeChartItem: Entry? = null
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        beforeChartItem?.apply {
            icon = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.ic_de_selected_chart_item
            )
        }
        beforeChartItem = e
        e?.icon = ContextCompat.getDrawable(
            this,
            R.drawable.ic_selected_chart_item
        )
    }

    override fun onNothingSelected() {
        beforeChartItem?.apply {
            icon = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.ic_de_selected_chart_item
            )
        }
    }

}