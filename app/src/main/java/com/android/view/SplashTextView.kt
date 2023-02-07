package com.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by gsy on 17/10/9.
 * 自定义闪动的textView
 */
@SuppressLint("AppCompatCustomView")
class SplashTextView : TextView {
    // 构造方法，不这样写不认，也是醉了
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style)

    private var mPaint: TextPaint? = null                                       // 画笔
    private var mLinearGradient: LinearGradient? = null                         // 线线渐变器
    private var mViewWidth = 0                                                  // 保存textView的宽度
    private var mGradientMatrix: Matrix? = null                                 // 矩阵
    private var mTranslate: Int = 0                                             // 平移值

    /**
     * 复写onDraw方法，在绘制时进行自定义操作¬
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mGradientMatrix != null) {
            // 平移值默认为0，每100ms增加一次，每次增加五分之一的textView的宽度
            mTranslate += mViewWidth / 5
            // 当矩阵平移值超过textView的宽度时，将其置为负的宽度，也可以置为0，随意
            if (mTranslate > mViewWidth) mTranslate = -mViewWidth
            mGradientMatrix!!.setTranslate(mTranslate.toFloat(), 0f)            // 两个感叹号代表"我确定这个一定不为空"
            mLinearGradient?.setLocalMatrix(mGradientMatrix)                    // 问好代表"可能为空，如果不为空则执行操作"
            postInvalidateDelayed(100)
        }
    }

    /**
     * 复写onSizeChanged方法，当textView大小有改动时初始化宽度的信息
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = measuredWidth                                              // 获取到textView的宽高
        if (mViewWidth > 0) {
            mPaint = paint                                                      // 得到该textView的画笔
            mLinearGradient = LinearGradient(0f                                 // 初始化渐变器
                , 0f
                , mViewWidth.toFloat()
                , 0f
                , intArrayOf(Color.BLUE, Color.RED, Color.BLUE)
                , null
                , Shader.TileMode.CLAMP)
            mPaint?.shader = mLinearGradient                                    // 设置该textView的paint的shader
            mGradientMatrix = Matrix()                                          // 初始化矩阵
        }
    }

}
