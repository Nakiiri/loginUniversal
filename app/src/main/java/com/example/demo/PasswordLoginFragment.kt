package com.example.demo

import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.Navigation
import cn.leancloud.LCUser
import com.example.demo.ClassHelper.EditTextWatcherHelper
import com.example.demo.ClassHelper.EditTextWithClear
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PasswordLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasswordLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayout2: LinearLayout
    private lateinit var imageViewQuit: ImageView
    private lateinit var editTextAccount: EditTextWithClear
    private lateinit var editTextPassword: EditTextWithClear
    private lateinit var btnLogin: Button
    private lateinit var tvTip: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var loginrMotion: MotionLayout
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
        return inflater.inflate(R.layout.fragment_password_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageViewQuit = requireActivity().findViewById(R.id.imageView_quitLogin)
        editTextAccount = requireActivity().findViewById(R.id.et_accountLogin)
        editTextPassword = requireActivity().findViewById(R.id.et_passwordLogin)
        btnLogin = requireActivity().findViewById(R.id.button_login_execute)
        tvTip = requireActivity().findViewById(R.id.textView_loginTip)
        progressBar = requireActivity().findViewById(R.id.progressBar_login)
        loginrMotion = requireActivity().findViewById(R.id.loginMotionlayout)
        linearLayout = requireActivity().findViewById(R.id.linearLayout)
        linearLayout2 = requireActivity().findViewById(R.id.linearLayout2)

        btnLogin.isEnabled = false
        progressBar.visibility = View.INVISIBLE
        tvTip.visibility = View.INVISIBLE


        val hint = SpannableString(resources.getString(R.string.login_account_input))
        val hintSize = AbsoluteSizeSpan(12,true)
        hint.setSpan(hintSize,0,hint.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        editTextAccount.hint = SpannedString(hint)

        editTextAccount.tag = "phone_code"
        editTextPassword.tag = "password_code"
        textWatcherHelper.setTargetView(btnLogin).addEditTexts(editTextAccount,editTextPassword)

        imageViewQuit.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_passwordLoginFragment_to_loginMethodFragment) }

        val anim = AnimationUtils.loadAnimation(requireActivity(),R.anim.slide_to_right)

        btnLogin.setOnClickListener {
            val name = editTextAccount.text?.trim().toString()
            val pwd = editTextPassword.text?.trim().toString()

            loginrMotion.progress = 0f

            LCUser().apply {
                username = name
                password = pwd

                progressBar.visibility = View.VISIBLE

                LCUser.logIn(name,pwd).subscribe(object :Observer<LCUser>{
                    override fun onSubscribe(d: Disposable) { }

                    override fun onNext(t: LCUser) {
                        tvTip.text = "登陆成功"
                        tvTip.visibility = View.VISIBLE
                        linearLayout.startAnimation(anim)
                        linearLayout2.startAnimation(anim)
                        Handler().postDelayed({
                            linearLayout.visibility = View.VISIBLE
                            linearLayout2.visibility = View.VISIBLE},
                            300)
                        Navigation.findNavController(it).navigate(R.id.action_passwordLoginFragment_to_accountFragment)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message != "The username and password mismatch."){
                            tvTip.text = "${e.message}"
                        }
                        progressBar.visibility = View.INVISIBLE

                        tvTip.visibility = View.VISIBLE
                        loginrMotion.transitionToEnd()
                    }

                    override fun onComplete() { }

                })
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
         * @return A new instance of fragment PasswordLoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PasswordLoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}