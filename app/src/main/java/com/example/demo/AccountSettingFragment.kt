package com.example.demo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import cn.leancloud.LCUser
import java.time.Duration
import kotlinx.coroutines.delay as delay1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountSettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountSettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var toolbar: Toolbar
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayout2: LinearLayout
    private lateinit var constraintLayoutAboutUs: ConstraintLayout
    private lateinit var constraintLayoutUniversal: ConstraintLayout
    private lateinit var constraintLayoutAccountSafety: ConstraintLayout
    private lateinit var constraintLayoutFeedback: ConstraintLayout
    private lateinit var btn_quit:Button

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
        return inflater.inflate(R.layout.fragment_account_setting, container, false)
    }

    @SuppressLint("InflateParams")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        linearLayout = requireActivity().findViewById(R.id.linearLayout)
        linearLayout2 = requireActivity().findViewById(R.id.linearLayout2)
        constraintLayoutAboutUs = requireActivity().findViewById(R.id.constraintLayout_aboutUs)
        constraintLayoutUniversal = requireActivity().findViewById(R.id.constraintLayout_universal)
        constraintLayoutAccountSafety = requireActivity().findViewById(R.id.constraintLayout_accountSafety)
        constraintLayoutFeedback = requireActivity().findViewById(R.id.constraintLayout_feedback)
        btn_quit = requireActivity().findViewById(R.id.button_account_quit)

        val anim = AnimationUtils.loadAnimation(requireActivity(),R.anim.slide_to_right)

        toolbar = requireActivity().findViewById(R.id.toolbar_account_setting)
        toolbar.setNavigationOnClickListener {v ->
            linearLayout.startAnimation(anim)
            linearLayout2.startAnimation(anim)
            Handler().postDelayed({
                linearLayout.visibility = View.VISIBLE
                linearLayout2.visibility = View.VISIBLE},
                300)
            Navigation.findNavController(v).navigate(R.id.action_accountSettingFragment_to_accountFragment)}

        constraintLayoutAboutUs.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_accountSettingFragment_to_aboutUsFragment) }
        constraintLayoutUniversal.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_accountSettingFragment_to_universalSettingFragment) }
        constraintLayoutAccountSafety.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_accountSettingFragment_to_accountSafetyFragment) }
        constraintLayoutFeedback.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_accountSettingFragment_to_feedbackSettingFragment) }
        btn_quit.setOnClickListener { v ->
            val view = LayoutInflater.from(requireActivity()).inflate(R.layout.quit_dialog,null)
            AlertDialog.Builder(requireActivity())
                .setTitle("提示")
                .setView(view)
                .setPositiveButton("确定"
                ) { _, _ ->
                    LCUser.logOut()
                    linearLayout.startAnimation(anim)
                    linearLayout2.startAnimation(anim)
                    Handler().postDelayed({
                        linearLayout.visibility = View.VISIBLE
                        linearLayout2.visibility = View.VISIBLE},
                        300)
                    Navigation.findNavController(v).navigate(R.id.action_accountSettingFragment_to_accountFragment)
                }
                .setNegativeButton("取消"
                ) { p0, _ -> p0.dismiss() }
                .create()
                .show()

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountSettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountSettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}