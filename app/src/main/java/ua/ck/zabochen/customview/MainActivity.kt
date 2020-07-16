package ua.ck.zabochen.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.ck.zabochen.customview.view.CircleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    private fun showCircleView() {
        setContentView(CircleView(this))
    }
}