package dcuestab.gcuestab.com.components

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import dcuestab.gcuestab.com.components.timeline.model.TemporalLinePoint
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_timeline.*

class TimelineActivity : AppCompatActivity() {

    val TOTAL_TIME : Float = 1399.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_timeline)

        this.init()
    }

    fun init() {
        this.addSelectedArea()
        this.configTemporalLine()
        this.configSelectedAreaButton()
    }

    private fun addSelectedArea(){
        val point = TemporalLinePoint()
        point.temporalLineStart = 400.0f / TOTAL_TIME
        point.temporalLineEnd = 600.0f / TOTAL_TIME
        this.timeLineGroup.addSelectedTemporalLine(point)
    }

    private fun configTemporalLine() {
        val lBusyPoints = ArrayList<TemporalLinePoint>()

        val point0 = TemporalLinePoint()
        point0.temporalLineStart = 0.0f / TOTAL_TIME
        point0.temporalLineEnd = 300.0f / TOTAL_TIME
        lBusyPoints.add(point0)

        val point1 = TemporalLinePoint()
        point1.temporalLineStart = 400.0f / TOTAL_TIME
        point1.temporalLineEnd = 460.0f / TOTAL_TIME
        lBusyPoints.add(point1)

        val point2 = TemporalLinePoint()
        point2.temporalLineStart = 1000.0f / TOTAL_TIME
        point2.temporalLineEnd = 1399.0f / TOTAL_TIME
        lBusyPoints.add(point2)

        this.timeLine.listBusyArea = lBusyPoints
    }

    private fun configSelectedAreaButton() {
        this.selectAreaB.setOnClickListener {
            this.timeLineGroup.mPaintState = this.timeLineGroup.MEASURE_NO_PAINT

            if (this.timeLineGroup.hasSelectedArea())
                this.timeLineGroup.removeSelectedTemporalLine()
            else {
                this.addSelectedArea()
            }
        }
    }
}