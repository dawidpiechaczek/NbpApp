package pl.piechaczek.dawid.nbpapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.piechaczek.dawid.core.ui.OnSegmentChangeListener
import pl.piechaczek.dawid.core.ui.SegmentedButtonGroup

class MainActivity : AppCompatActivity(), OnSegmentChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val group = SegmentedButtonGroup(
            listOf(
                SegmentedButtonGroup.SegmentedButton("Tabela A", R.color.colorAccent),
                SegmentedButtonGroup.SegmentedButton("Tabela B", R.color.colorPrimary)
            ),
            this, this
        )
        findViewById<LinearLayout>(R.id.segmented).addView(group)
    }

    override fun onSegmentChange(previousItemIndex: Int, newItemIndex: Int) {
        Toast.makeText(
            applicationContext,
            "previous: $previousItemIndex, next: $newItemIndex",
            Toast.LENGTH_LONG
        ).show()
    }
}