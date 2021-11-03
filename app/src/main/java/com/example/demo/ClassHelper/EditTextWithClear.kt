package com.example.demo.ClassHelper

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.demo.R

class EditTextWithClear @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {
    private var iconDrawable:Drawable? = null
    //private var verifyMobile:Boolean = false

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear, 0, 0)
            .apply {
                try {
                    val iconId = getResourceId(R.styleable.EditTextWithClear_clearIcon,0)
                    if (iconId !=0){
                        iconDrawable = ContextCompat.getDrawable(context,iconId)
                    }
                } finally {
                    recycle()
                }
            }

    }


    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
//        if (lengthAfter == 11){
//            verifyMobile = editTextVerify()
//        }
        toggleClearIcon()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        event.let { e->
            iconDrawable?.let {
                if(e.action == MotionEvent.ACTION_UP
                    &&e.x > width - it.intrinsicWidth -20
                    && e.x < width + 20
                    && e.y > (height - it.intrinsicHeight)/2 - 20
                    && e.y < height/2 +it.intrinsicHeight/2 + 20
                ){
                    text?.clear()
                }
            }
        }
        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        toggleClearIcon()
    }

    private fun toggleClearIcon(){
        val icon  = if(isFocused && text?.isNotEmpty() == true) iconDrawable else null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,icon,null)
        }
    }

//    private fun editTextVerify():Boolean{
//            val pattern = Pattern.compile("1[0-9]{10}")
//            val matcher = pattern.matcher(text)
//            return matcher.matches()
//    }
}