package com.example.demo

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginMethodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginMethodFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var imageViewExitLogin:ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayout2: LinearLayout
    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button

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
        return inflater.inflate(R.layout.fragment_login_method, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        linearLayout = requireActivity().findViewById(R.id.linearLayout)
        linearLayout2 = requireActivity().findViewById(R.id.linearLayout2)
        imageViewExitLogin = requireActivity().findViewById(R.id.imageView_exitLogin)
        buttonRegister = requireActivity().findViewById(R.id.button_register)
        buttonLogin = requireActivity().findViewById(R.id.button_login)

        val animExitLogin = AnimationUtils.loadAnimation(requireActivity(),R.anim.slide_to_bottom)

        imageViewExitLogin.setOnClickListener { v->
            linearLayout.startAnimation(animExitLogin)
            linearLayout2.startAnimation(animExitLogin)
            Handler().postDelayed({
                linearLayout.visibility = View.VISIBLE
                linearLayout2.visibility = View.VISIBLE},
                300)
            Navigation.findNavController(v).navigate(R.id.action_loginMethodFragment_to_accountFragment)
        }

        buttonRegister.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_loginMethodFragment_to_registerFragment) }

        buttonLogin.setOnClickListener { v -> Navigation.findNavController(v).navigate(R.id.action_loginMethodFragment_to_passwordLoginFragment) }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginMethodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginMethodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}