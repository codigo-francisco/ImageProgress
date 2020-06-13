package com.rockbass.imageprogress

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toDrawable
import kotlinx.coroutines.*
import kotlin.math.roundToInt

class ImageProgress : AppCompatImageView {

    private val scope = MainScope()

    private var originalBitmap : Bitmap
    private var canvas: Canvas? = null
    private val paint: Paint = Paint()
    private var rectColor: Rect
    private var rectBW: Rect
    private val colorMatrix = ColorMatrix()

    var value : Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
            scope.launch {
                setImageBitmap(changeImage())
            }
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    init {
        originalBitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, false)
        rectColor = Rect(0,0,0,originalBitmap.height)
        rectBW = Rect(0, 0, originalBitmap.width, originalBitmap.height)
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
    }

    private fun init(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ImageProgress, 0, 0).apply {
            value = getInt(R.styleable.ImageProgress_value, 0)
        }.recycle()
    }

    private suspend fun changeImage() = withContext(Dispatchers.Default) {
        val bitmapResult = Bitmap.createBitmap(
            originalBitmap.width,
            originalBitmap.height,
            originalBitmap.config
        )
        canvas = Canvas(bitmapResult)

        val position = (originalBitmap.width * (value / 100f)).roundToInt()

        rectColor.right = position
        rectBW.left = position

        colorMatrix.setSaturation(1f)
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas?.drawBitmap(originalBitmap, rectColor, rectColor, paint)

        colorMatrix.setSaturation(0f)
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas?.drawBitmap(originalBitmap, rectBW, rectBW, paint)

        bitmapResult
    }
}