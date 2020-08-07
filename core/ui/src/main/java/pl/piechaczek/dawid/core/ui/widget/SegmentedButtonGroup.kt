package pl.piechaczek.dawid.core.ui.widget

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlinx.android.parcel.Parcelize

class SegmentedButtonGroup @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributes, defStyleAttr) {

    private var currentPosition: Int = 0
    private var onSegmentChangeListener: OnSegmentChangeListener? = null
    private val segmentedButtons: MutableList<SegmentedButton> = mutableListOf()

    init {
        orientation = HORIZONTAL
        initButtons()
    }

    fun setSegmentChangeListener(onSegmentChangeListener: OnSegmentChangeListener) {
        this.onSegmentChangeListener = onSegmentChangeListener
    }

    fun setSegmentedButtons(segmentedButtons: List<SegmentedButton>) {
        this.segmentedButtons.clear()
        this.segmentedButtons.addAll(segmentedButtons)
        initButtons()
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return SavedState(
            currentPosition,
            superState
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState

        super.onRestoreInstanceState(savedState.state)
        currentPosition = savedState.position
    }

    private fun initButtons() {
        segmentedButtons.forEach { segmentedButton ->
            Button(context)
                .apply {
                    layoutParams =
                        LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                    text = segmentedButton.text
                    setBackgroundColor(ContextCompat.getColor(context, segmentedButton.color))
                    setOnClickListener {
                        val newPosition = segmentedButtons.indexOf(segmentedButton)
                        onSegmentChangeListener?.onSegmentChange(currentPosition, newPosition)
                        currentPosition = newPosition
                    }
                }.also {
                    this.addView(it)
                }
        }
    }

    fun getItemsCount(): Int = segmentedButtons.size
    fun getCurrentItemIndex(): Int = currentPosition
    fun setCurrentItemIndex(newItemIndex: Int) {
        currentPosition = newItemIndex
    }

    fun getCurrentItem(): SegmentedButton = segmentedButtons[currentPosition]
    fun getItemAt(index: Int): SegmentedButton = segmentedButtons[index]

    @Parcelize
    data class SegmentedButton(val text: String, @ColorRes val color: Int, val tableType: Char) : Parcelable

    @Parcelize
    data class SavedState(val position: Int, val state: Parcelable?) : Parcelable
}