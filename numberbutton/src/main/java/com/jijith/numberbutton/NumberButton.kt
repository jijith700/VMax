package com.jijith.numberbutton

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class NumberButton : RelativeLayout {

    internal var context: Context
    internal var attrs: AttributeSet? = null
    internal var styleAttr: Int = 0
    internal var mListener: OnClickListener? = null
    internal var initialNumber: Int = 0
    internal var lastNumber: Int = 0
    internal var currentNumber: Int = 0
    internal var finalNumber: Int = 0
    internal var textView: TextView? = null
    internal var addBtn: Button? = null
    internal var subtractBtn:Button? = null
    internal var view: View? = null
    internal var mOnValueChangeListener: OnValueChangeListener? = null

    constructor(context: Context): super(context)  {
        this.context = context
        initView()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    {
        this.context = context
        this.attrs = attrs
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    {
        this.context = context
        this.attrs = attrs
        this.styleAttr = defStyleAttr
        initView()
    }

    private fun initView() {
        this.view = this
        View.inflate(context, R.layout.layout_number_button, this)
        val res = resources
        val defaultColor = res.getColor(android.R.color.black)
        val defaultTextColor = res.getColor(android.R.color.white)
        val defaultDrawable = res.getDrawable(R.drawable.bg_number_button)

        val a = context.obtainStyledAttributes(attrs, R.styleable.NumberButton, styleAttr, 0)

        initialNumber = a.getInt(R.styleable.NumberButton_initialNumber, 0)
        finalNumber = a.getInt(R.styleable.NumberButton_finalNumber, Integer.MAX_VALUE)
        val textSize = a.getDimension(R.styleable.NumberButton_textSize, 13f)
        val color = a.getColor(R.styleable.NumberButton_backGroundColor, defaultColor)
        val textColor = a.getColor(R.styleable.NumberButton_textColor, defaultTextColor)
        var drawable = a.getDrawable(R.styleable.NumberButton_backgroundDrawable)

        subtractBtn = findViewById<View>(R.id.subtract_btn) as Button
        addBtn = findViewById<View>(R.id.add_btn) as Button
        textView = findViewById<View>(R.id.number_counter) as TextView
        val mLayout = findViewById<View>(R.id.layout) as LinearLayout

        subtractBtn!!.setTextColor(textColor)
        addBtn!!.setTextColor(textColor)
        textView!!.setTextColor(textColor)
        subtractBtn!!.setTextSize(textSize)
        addBtn!!.textSize = textSize
        textView!!.textSize = textSize

        if (drawable == null) {
            drawable = defaultDrawable
        }
//        assert(drawable != null)
        drawable!!.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC)
            mLayout.background = drawable

        textView!!.text = initialNumber.toString()

        currentNumber = initialNumber
        lastNumber = initialNumber

        subtractBtn!!.setOnClickListener(OnClickListener {
            val num = Integer.valueOf(textView!!.text.toString())
            setNumber((num - 1).toString(), true)
        })
        addBtn!!.setOnClickListener {
            val num = Integer.valueOf(textView!!.text.toString())
            setNumber((num + 1).toString(), true)
        }
        a.recycle()
    }

    private fun callListener(view: View) {
        if (mListener != null) {
            mListener!!.onClick(view)
        }

        if (mOnValueChangeListener != null) {
            if (lastNumber != currentNumber) {
                mOnValueChangeListener!!.onValueChange(this, lastNumber, currentNumber)
            }
        }
    }

    fun getStringNumber(): String {
        return currentNumber.toString()
    }

    fun getIntNumber(): Int {
        return currentNumber
    }

    fun setNumber(number: String) {
        lastNumber = currentNumber
        this.currentNumber = Integer.parseInt(number)
        if (this.currentNumber > finalNumber) {
            this.currentNumber = finalNumber
        }
        if (this.currentNumber < initialNumber) {
            this.currentNumber = initialNumber
        }
        textView!!.text = currentNumber.toString()
    }

    fun setNumber(number: Int) {
        lastNumber = currentNumber
        this.currentNumber = number
        if (this.currentNumber > finalNumber) {
            this.currentNumber = finalNumber
        }
        if (this.currentNumber < initialNumber) {
            this.currentNumber = initialNumber
        }
        textView!!.text = currentNumber.toString()
    }

    fun setMaxNumber(number: Int) {
        this.finalNumber = number
    }

    fun setNumber(number: String, notifyListener: Boolean) {
        setNumber(number)
        if (notifyListener) {
            callListener(this)
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.mListener = onClickListener
    }

    fun setOnValueChangeListener(onValueChangeListener: OnValueChangeListener) {
        mOnValueChangeListener = onValueChangeListener
    }

    interface OnClickListener {
        fun onClick(view: View)
    }

    interface OnValueChangeListener {
        fun onValueChange(view: NumberButton, oldValue: Int, newValue: Int)
    }

    fun setRange(startingNumber: Int?, endingNumber: Int?) {
        this.initialNumber = startingNumber!!
        this.finalNumber = endingNumber!!
        this.currentNumber = startingNumber

        textView!!.text = startingNumber.toString()
    }

    fun updateColors(backgroundColor: Int, textColor: Int) {
        this.textView!!.setBackgroundColor(backgroundColor)
        this.addBtn!!.setBackgroundColor(backgroundColor)
        this.subtractBtn!!.setBackgroundColor(backgroundColor)

        this.textView!!.setTextColor(textColor)
        this.addBtn!!.setTextColor(textColor)
        this.subtractBtn!!.setTextColor(textColor)
    }

    fun updateTextSize(unit: Int, newSize: Float) {
        this.textView!!.setTextSize(unit, newSize)
        this.addBtn!!.setTextSize(unit, newSize)
        this.subtractBtn!!.setTextSize(unit, newSize)
    }
}