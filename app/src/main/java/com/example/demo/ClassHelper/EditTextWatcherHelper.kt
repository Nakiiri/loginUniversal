package com.example.demo.ClassHelper

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import java.util.regex.Pattern

class EditTextWatcherHelper : TextWatcher{
    private val editViewList = arrayListOf<TextView>()

    private var targetView: View? = null

    fun setTargetView(targetView:View?): EditTextWatcherHelper {
        this.targetView = targetView
        return this
    }

    fun addEditTexts(vararg editViews: TextView){
        if (editViews.isNullOrEmpty())
            return

        for(editText in editViews){
            editText.addTextChangedListener(this)
            editViewList.add(editText)
        }

        afterTextChanged(null)
    }

    fun removeAllViews(){
        if (editViewList.isEmpty())
            return

        for (editText in editViewList){
            editText.removeTextChangedListener(this)
        }

        editViewList.clear()
    }

    private fun setEnable(enabled : Boolean){
        if (enabled ==targetView?.isEnabled)
            return

        targetView?.isEnabled = enabled
    }



    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        if (editViewList.isEmpty())
            return

        for (editText in editViewList){
            val text = editText.text?.toString()?.trim()
            if (text.isNullOrEmpty()){
                setEnable(false)
                return
            }

            if (editText.tag == "phone_code"){
                if(isMobile(editText.text?.toString()?.trim())){
                    setEnable(true)
                }else{
                    setEnable(false)
                    return
                }
            }

            if (editText.tag == "password_code"){
                if (editText.text?.trim().toString().length >= 6){
                    setEnable(true)
                }else{
                    setEnable(false)
                    return
                }
            }
        }
        setEnable(true)
    }

    fun isMobile(str: String?): Boolean {
        //1开头第二位34578
        val pattern = Pattern.compile("1[3|4|5|7|8][0-9]{9}")           //1[0-9]{10} *1开头11位
        val matcher = pattern.matcher(str)
        return matcher.matches()
    }
}