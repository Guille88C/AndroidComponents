package dcuestab.gcuestab.com.components

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_loader.*


class LoaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)

        initLoader()
        initAnimatedLoader()
    }




    private fun initLoader() {
        progressET.setText(loader.current.toString())
        totalET.setText(loader.total.toString())

        applyB.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(applyB.windowToken, 0)

            loader.changeProgress(
                    progressET.text.toString().toFloat(),
                    totalET.text.toString().toFloat()
            )
        }
    }

    private fun initAnimatedLoader() {
        animatedProgressET.setText(animatedLoader.finalCurrent.toString())
        animatedTotalET.setText(animatedLoader.total.toString())
        animatedProgressPerSecondET.setText(animatedLoader.progressPerSecond.toString())

        animatedApplyB.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(applyB.windowToken, 0)

            animatedLoader.changeProgress(
                    animatedProgressET.text.toString().toFloat(),
                    animatedTotalET.text.toString().toFloat(),
                    animatedProgressPerSecondET.text.toString().toFloat()
            )
        }
    }
}