package com.example.demo

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import cn.leancloud.LCObject
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import com.example.demo.ViewModel.SurveyViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountSurveyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountSurveyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var toolbar: Toolbar
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayout2: LinearLayout
    private lateinit var surveyViewModel:SurveyViewModel
    private lateinit var tvNatureTip:TextView
    private lateinit var tvSensitiveTip:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_survey, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        linearLayout = requireActivity().findViewById(R.id.linearLayout)
        linearLayout2 = requireActivity().findViewById(R.id.linearLayout2)
        toolbar = requireActivity().findViewById(R.id.toolbar_account_survey)
        tvNatureTip = requireActivity().findViewById(R.id.textView_surveyNatureTip)
        tvSensitiveTip = requireActivity().findViewById(R.id.textView_surveySensitiveTip)

        surveyViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(SurveyViewModel::class.java)

        val anim = AnimationUtils.loadAnimation(requireActivity(),R.anim.slide_to_right)

        toolbar.setNavigationOnClickListener {v ->
            linearLayout.startAnimation(anim)
            linearLayout2.startAnimation(anim)
            Handler().postDelayed({
                linearLayout.visibility = View.VISIBLE
                linearLayout2.visibility = View.VISIBLE},
                300)
            Navigation.findNavController(v).navigate(R.id.action_accountSurveyFragment_to_accountFragment)}




//是否已完成问卷
        surveyViewModel.queryIfUserNatureComplete(LCUser.getCurrentUser().username.toString())
        surveyViewModel.dataList.observe(requireActivity(),{
            if(surveyViewModel.dataList.value?.get(0)?.get("surveyNature").toString().toInt()==100){
                tvNatureTip.text = getString(R.string.survey_NotComplete_tip)
            }else{
                tvNatureTip.text = getString(R.string.survey_complete_tip)
            }

            if(surveyViewModel.dataList.value?.get(0)?.get("surveySensitive").toString().toInt()==100){
                tvSensitiveTip.text = getString(R.string.survey_NotComplete_tip)
            }else{
                tvSensitiveTip.text = getString(R.string.survey_complete_tip)
            }
        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountSurveyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountSurveyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}