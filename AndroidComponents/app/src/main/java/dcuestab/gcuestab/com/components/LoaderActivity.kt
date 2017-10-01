package dcuestab.gcuestab.com.components

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dcuestab.gcuestab.com.components.loader.listeners.AnimatedArcLoaderProgressListener

import kotlinx.android.synthetic.main.activity_loader.*

class LoaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)

        loader.progressListener = object : AnimatedArcLoaderProgressListener {
            override fun onProgress(progress: Int) {
                text.text = progress.toString()
            }

        }


    }
}