package com.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.TextView
import kotlin.random.Random


/**
 * 简单的自定义view  继承自TextView
 * 增加功能，点击改变文字颜色
 * 变色模式:0-随机变色  1-顺序变色
 * 颜色集合可输入
 */
@SuppressLint("AppCompatCustomView")
class CustomTextView : TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style)

    enum class ChangeMode(i: Int) {
        MODE_RANDOM(0),//随机
        MODE_ORDER(1)//顺序
    }

    /**
     * 文字颜色集合,默认四种颜色
     */
    private var colorArray: ArrayList<Int> =
        arrayListOf(Color.BLUE, Color.RED, Color.GREEN, Color.BLACK)
        private get
        set(value) {
            if (value is ArrayList<Int>) {
                when {
                    value.equals(colorArray) -> return//如果传入的和默认的一样就没必要改变
                    else -> {
                        colorArray.clear()
                        colorArray.addAll(value)
                    }
                }
            }
        }

    /**
     * 变色模式 0-随机变色  1-顺序变色
     */
    private var changeMode = ChangeMode.MODE_RANDOM
        private get() = field
        set(value) {
            if (value is ChangeMode) field = value
        }

    /**
     * 顺序模式下，当前颜色index
     */
    private var colorIndex = 0


    init {
        initAttrs()
    }

    /**
     * 初始话 自定义属性
     */
    private fun initAttrs() {
    }

    private var clickTime = 0L
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_DOWN -> {
                clickTime = System.currentTimeMillis()
                if (!isClickable) {
                    if (isTouchPointInView(this,event.x,event.y)){
                        return true
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                onClickText()
                var delTime = System.currentTimeMillis() - clickTime
                clickTime = 0L
                if (delTime < ViewConfiguration.getTapTimeout() && isTouchPointInView(
                        this,
                        event.x,
                        event.y
                    )
                ) {
                    onClickText()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 判断点击是否在view 的范围内
     */
    private fun isTouchPointInView(view: TextView, x: Float, y: Float): Boolean {
        if (view == null) return false
        var location = IntArray(2)
        view.getLocationOnScreen(location)
        var left = location.get(0)
        var top = location.get(1)
        var right = left + view.measuredWidth
        var bottom = top + view.measuredHeight

        if (y >= top && y <= bottom && x >= left && x <= right) return true
        return false
    }

    fun onClickText() {
        when (changeMode) {
            ChangeMode.MODE_RANDOM -> {
                var len = colorArray.size
                var index = Random.nextInt(len)

                setTextColor(colorArray.get(index))
//                invalidate()//不知道要不要刷新
            }

            ChangeMode.MODE_ORDER -> {
                if (colorIndex >= colorArray.size) colorIndex = 0
                setTextColor(colorArray.get(colorIndex))
                colorIndex++
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

}