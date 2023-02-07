package com.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TextView
import kotlin.random.Random


/**
 * 简单的自定义view  继承自TextView
 * 增加功能，点击改变文字颜色
 * 变色模式:0-随机变色  1-顺序变色
 * 颜色集合可输入
 */
@SuppressLint("AppCompatCustomView")
class MyTextView : TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style)

//    enum class ChangeMode(i: Int) {
//        MODE_RANDOM(0),//随机
//        MODE_ORDER(1)//顺序
//    }

    /**
     * 文字颜色集合,默认四种颜色
     */
    private var colorArray: ArrayList<Int> =
        listOf<Int>(Color.BLUE, Color.RED, Color.GREEN, Color.BLACK) as ArrayList<Int>
//        private get
//        set(value) {
//            if (value is ArrayList<Int>) {
//                when {
//                    value.equals(colorArray) -> return//如果传入的和默认的一样就没必要改变
//                    else -> {
//                        colorArray.clear()
//                        colorArray.addAll(value)
//                    }
//                }
//            }
//        }

    /**
     * 变色模式 0-随机变色  1-顺序变色
     */
//    private var changeMode = ChangeMode.MODE_RANDOM
//        private get() = field
//        set(value) {
//            if (value is ChangeMode) field = value
//        }

    /**
     * 顺序模式下，当前颜色index
     */
    private var colorIndex = 0



//
//    init {
//        initAttrs()
//    }
//
//    /**
//     * 初始话 自定义属性
//     */
//    private fun initAttrs() {
//        TODO("Not yet implemented")
//    }
//
//    override fun callOnClick(): Boolean {
//        onClickText()
//        return super.callOnClick()
//    }

    fun onClickText() {
//        when (changeMode) {
//            ChangeMode.MODE_RANDOM -> {
//                var len = colorArray.size
//                var index = Random.nextInt(len)
//
//                setTextColor(colorArray.get(index))
//                invalidate()//不知道要不要刷新
//            }
//
//            ChangeMode.MODE_ORDER -> {
//
//                if (colorIndex >= colorArray.size) colorIndex = 0
//                setTextColor(colorArray.get(colorIndex))
//                colorIndex++
//            }
//        }
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