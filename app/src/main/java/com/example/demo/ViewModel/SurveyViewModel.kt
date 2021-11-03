package com.example.demo.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.leancloud.LCObject
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import com.example.demo.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class SurveyViewModel(application: Application) : AndroidViewModel(application) {
    private val _dataList = MutableLiveData<List<LCObject>>()
    val dataList : MutableLiveData<List<LCObject>> = _dataList
    private var completeTip : String = ""
    private var notCompleteTip : String = ""
    init {
        completeTip = application.getString(R.string.survey_complete_tip)
        notCompleteTip = application.getString(R.string.survey_NotComplete_tip)

        val query = LCQuery<LCObject>("SurveyResult")
        //query.whereEqualTo("username",LCUser.getCurrentUser().username)
        query.findInBackground().subscribe(object :Observer<List<LCObject>>{
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: List<LCObject>) {
                _dataList.value = t
            }

            override fun onError(e: Throwable) {
                Toast.makeText(application, "${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}
        })
    }

    fun addInitSurvey(name:String){
        LCObject("SurveyResult").apply {
            put("username",name)
            saveInBackground().subscribe(object :Observer<LCObject>{
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: LCObject) { }

                override fun onError(e: Throwable) {
                    Toast.makeText(getApplication(), "${e.message}", Toast.LENGTH_SHORT).show()                }

                override fun onComplete() {}

            })
        }
    }

    fun queryIfUserNatureComplete(name: String){
        val query = LCQuery<LCObject>("SurveyResult")
        query.whereEqualTo("username",name)
        query.findInBackground().subscribe(object :Observer<List<LCObject>>{
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: List<LCObject>) {
                _dataList.value = t
            }

            override fun onError(e: Throwable) { }

            override fun onComplete() {}

        })

    }


}