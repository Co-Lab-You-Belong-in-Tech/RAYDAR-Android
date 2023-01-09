package ukponahiunsijeffery.example.raydar.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ukponahiunsijeffery.example.raydar.R

enum class ClickedState(val label: Int) {

    DEFAULT_STATE(R.string.default_state),
    FIRST_INCREMENT(R.string.first_increment),
    SECOND_INCREMENT(R.string.second_increment);

    fun next() = when (this) {
        DEFAULT_STATE -> FIRST_INCREMENT
        FIRST_INCREMENT -> SECOND_INCREMENT
        SECOND_INCREMENT -> DEFAULT_STATE
    }
}

class PriceButtonCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var buttonPriceLevel = 0

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        textSize = 50.0f
    }

    private var clickedState = ClickedState.DEFAULT_STATE

    init {
        isClickable = true
    }

    fun updateButtonState() {
        clickedState = clickedState.next()
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {

        if (clickedState == ClickedState.DEFAULT_STATE) {
            paint.color = resources.getColor(R.color.green)
            buttonPriceLevel = 0

            canvas?.drawRect(
                0F, 0F, (width * (33.333f / 100)),
                ((height * 1.0).toFloat()), paint
            )
        }

        if (clickedState == ClickedState.FIRST_INCREMENT) {
            paint.color = resources.getColor(R.color.green)
            buttonPriceLevel = 1

            canvas?.drawRect(
                0F, 0F, (width * (66.666f / 100)),
                ((height * 1.0).toFloat()), paint
            )
        }

        if (clickedState == ClickedState.SECOND_INCREMENT) {
            paint.color = resources.getColor(R.color.green)
            buttonPriceLevel = 2

            canvas?.drawRect(
                0F, 0F, (width * (100f / 100)),
                ((height * 1.0).toFloat()), paint
            )
        }


        super.onDraw(canvas)
    }


}
