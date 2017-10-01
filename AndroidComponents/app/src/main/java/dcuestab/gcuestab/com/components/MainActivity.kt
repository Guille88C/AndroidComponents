package dcuestab.gcuestab.com.components

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        timelineButton.setOnClickListener {
            startActivity(Intent(this, TimelineActivity::class.java))
        }

        loaderButton.setOnClickListener {
            startActivity(Intent(this, LoaderActivity::class.java))
        }
    }
}
