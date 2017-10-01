package dcuestab.gcuestab.com.components

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.tanuki.tanucomponents.views.AnimatedArcLoaderListener
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_loader.*

class LoaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_loader)

        loader.listener = object : AnimatedArcLoaderListener {
            override fun onProgress(progress: Int) {
                text.text = progress.toString()
            }

        }
    }
}