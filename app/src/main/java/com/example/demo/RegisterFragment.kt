package com.example.demo

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import cn.leancloud.LCUser
import com.example.demo.ClassHelper.EditTextWatcherHelper
import com.example.demo.ClassHelper.EditTextWithClear
import com.example.demo.ViewModel.SurveyViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var imageViewQuit: ImageView
    private lateinit var editTextAccount: EditTextWithClear
    private lateinit var editTextPassword: EditTextWithClear
    private lateinit var editTextPasswordVerify: EditTextWithClear
    private lateinit var btnRegister:Button
    private lateinit var progressBar:ProgressBar
    private lateinit var tvTip:TextView
    private lateinit var registerMotion:MotionLayout
    private lateinit var surveyViewModel: SurveyViewModel
    private val textWatcherHelper by lazy { EditTextWatcherHelper() }


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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageViewQuit = requireActivity().findViewById(R.id.imageView_quitRegister)
        editTextAccount = requireActivity().findViewById(R.id.et_account)
        btnRegister = requireActivity().findViewById(R.id.button_register_execute)
        editTextPassword = requireActivity().findViewById(R.id.et_password)
        editTextPasswordVerify = requireActivity().findViewById(R.id.et_passwordVerify)
        progressBar = requireActivity().findViewById(R.id.progressBar_register)
        tvTip = requireActivity().findViewById(R.id.textView_signUpTip)
        registerMotion = requireActivity().findViewById(R.id.registerMotionlayout)

        surveyViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(SurveyViewModel::class.java)


        btnRegister.isEnabled = false
        progressBar.visibility = View.INVISIBLE
        tvTip.visibility = View.INVISIBLE

        //设置hint字体大小
        val hint = SpannableString(resources.getString(R.string.login_account_input))
        val hintSize = AbsoluteSizeSpan(12,true)
        hint.setSpan(hintSize,0,hint.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        editTextAccount.hint = SpannedString(hint)



        //监听三个输入
        editTextAccount.tag = "phone_code"
        editTextPassword.tag = "password_code"
        editTextPasswordVerify.tag = "password_code"
        textWatcherHelper.setTargetView(btnRegister).addEditTexts(editTextAccount,editTextPassword,editTextPasswordVerify)

        imageViewQuit.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginMethodFragment) }

        btnRegister.setOnClickListener {
            val name = editTextAccount.text?.trim().toString()
            val pwd = editTextPassword.text?.trim().toString()
            val psdVer = editTextPasswordVerify.text?.trim().toString()

            //tip保持初始状态
            registerMotion.progress = 0f

            if (pwd == psdVer){
                LCUser().apply {
                    username = name
                    password = pwd

                    progressBar.visibility = View.VISIBLE


                    signUpInBackground().subscribe(object : Observer<LCUser>{
                        override fun onSubscribe(d: Disposable) { }

                        override fun onNext(t: LCUser) {
                            tvTip.text = "注册成功"
                            tvTip.visibility = View.VISIBLE
                            surveyViewModel.addInitSurvey(name)

                            LCUser.logIn(name,pwd).subscribe(object  : Observer<LCUser>{
                                override fun onSubscribe(d: Disposable) { }

                                override fun onNext(t: LCUser) {
                                    Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_accountFragment)
                                }

                                override fun onError(e: Throwable) {
                                    progressBar.visibility = View.INVISIBLE
                                    tvTip.text = "${e.message}"
                                    tvTip.visibility = View.VISIBLE
                                    registerMotion.transitionToEnd()
                                }

                                override fun onComplete() { }

                            })
                        }

                        override fun onError(e: Throwable) {
                            progressBar.visibility = View.INVISIBLE
                            tvTip.text = "${e.message}"
                            tvTip.visibility = View.VISIBLE
                            registerMotion.transitionToEnd()
                        }

                        override fun onComplete() { }

                    })
                }
            }else{
                tvTip.visibility = View.VISIBLE
                registerMotion.transitionToEnd()
            }

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}