package com.example.demo

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import cn.leancloud.LCUser
import com.google.android.material.internal.NavigationMenuItemView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var toolbar: Toolbar
    private lateinit var item:MenuItem
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayout2: LinearLayout
    private lateinit var textViewAttention:TextView
    private lateinit var textViewCollect:TextView
    private lateinit var textViewBeAttention:TextView
    private lateinit var textViewSupport:TextView
    private lateinit var itemAlert:MenuItem
    private lateinit var textViewLogin: TextView
    private lateinit var imageViewAccount: ImageView
    private lateinit var imgViewSurvey: ImageView
    private lateinit var tvSurvey:TextView


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
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.account_menu,menu);


    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)//回调onCreateOptionsMenu
        toolbar = requireActivity().findViewById(R.id.toolbar_account)
        toolbar.inflateMenu(R.menu.account_menu)
        linearLayout = requireActivity().findViewById(R.id.linearLayout)
        linearLayout2 = requireActivity().findViewById(R.id.linearLayout2)
        textViewAttention = requireActivity().findViewById(R.id.textView_attentionNumber)
        textViewCollect = requireActivity().findViewById(R.id.textView_collectNumber)
        textViewBeAttention = requireActivity().findViewById(R.id.textView_beAttentionNumber)
        textViewSupport = requireActivity().findViewById(R.id.textView_supportNumber)
        textViewLogin = requireActivity().findViewById(R.id.textView_accountLogin)
        imageViewAccount = requireActivity().findViewById(R.id.imageView_accountImage)
        imgViewSurvey = requireActivity().findViewById(R.id.imageView_survey)
        tvSurvey = requireActivity().findViewById(R.id.textView_survey)

        val anim = AnimationUtils.loadAnimation(requireActivity(),R.anim.slide_from_right)
        val animLogin = AnimationUtils.loadAnimation(requireActivity(),R.anim.slide_from_bottom)

        if (LCUser.getCurrentUser()!=null){
            textViewLogin.text = LCUser.getCurrentUser()?.username
        }


        item = toolbar.menu.findItem(R.id.account_setting)
        item.actionView.setOnClickListener{ v->
            linearLayout.visibility = View.GONE
            linearLayout2.visibility = View.GONE
            if (LCUser.getCurrentUser()!=null){
                linearLayout.startAnimation(anim)
                linearLayout2.startAnimation(anim)
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountSettingFragment)
            }else{
                linearLayout.startAnimation(animLogin)
                linearLayout2.startAnimation(animLogin)
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_loginMethodFragment)
            }

        }

        //登录用测试alert
//        itemAlert = toolbar.menu.findItem(R.id.account_alert)
//        itemAlert.actionView.setOnClickListener { v->
//            linearLayout.startAnimation(animLogin)
//            linearLayout2.startAnimation(animLogin)
//            linearLayout.visibility = View.GONE
//            linearLayout2.visibility = View.GONE
//            Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_loginMethodFragment)
//        }

        textViewAttention.setOnClickListener { v->
            linearLayout.startAnimation(anim)
            linearLayout2.startAnimation(anim)
            linearLayout.visibility = View.GONE
            linearLayout2.visibility = View.GONE
            Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountAttentionFragment)
        }

        textViewCollect.setOnClickListener { v->
            linearLayout.startAnimation(anim)
            linearLayout2.startAnimation(anim)
            linearLayout.visibility = View.GONE
            linearLayout2.visibility = View.GONE
            Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountCollectFragment)
        }

        textViewBeAttention.setOnClickListener { v->
            linearLayout.startAnimation(anim)
            linearLayout2.startAnimation(anim)
            linearLayout.visibility = View.GONE
            linearLayout2.visibility = View.GONE
            Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountBeAttentionFragment)
        }

        textViewSupport.setOnClickListener { v->
            linearLayout.startAnimation(anim)
            linearLayout2.startAnimation(anim)
            linearLayout.visibility = View.GONE
            linearLayout2.visibility = View.GONE
            Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountSupportFragment)
        }


        textViewLogin.setOnClickListener { v->
            if (LCUser.getCurrentUser()!=null){
                textViewLogin.text = LCUser.getCurrentUser().username
            }else{
                linearLayout.startAnimation(animLogin)
                linearLayout2.startAnimation(animLogin)
                linearLayout.visibility = View.GONE
                linearLayout2.visibility = View.GONE
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_loginMethodFragment)
            }

        }

        imageViewAccount.setOnClickListener { v->
            if(LCUser.getCurrentUser()==null){
                linearLayout.startAnimation(animLogin)
                linearLayout2.startAnimation(animLogin)
                linearLayout.visibility = View.GONE
                linearLayout2.visibility = View.GONE
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_loginMethodFragment)
            }
        }

        imgViewSurvey.setOnClickListener { v->
            linearLayout.visibility = View.GONE
            linearLayout2.visibility = View.GONE
            if (LCUser.getCurrentUser()!=null){
                linearLayout.startAnimation(anim)
                linearLayout2.startAnimation(anim)
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountSurveyFragment)
            }else{
                linearLayout.startAnimation(animLogin)
                linearLayout2.startAnimation(animLogin)
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_loginMethodFragment)
            }
        }

        tvSurvey.setOnClickListener { v->
            linearLayout.visibility = View.GONE
            linearLayout2.visibility = View.GONE
            if (LCUser.getCurrentUser()!=null){
                linearLayout.startAnimation(anim)
                linearLayout2.startAnimation(anim)
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountSurveyFragment)
            }else{
                linearLayout.startAnimation(animLogin)
                linearLayout2.startAnimation(animLogin)
                Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_loginMethodFragment)
            }
        }


//        val view:ImageFilterView = requireActivity().findViewById(R.id.imageView3)
//        view.setOnClickListener{v ->
//
//            Toast.makeText(requireActivity(), Navigation.findNavController(v).currentDestination?.id.toString(), Toast.LENGTH_SHORT).show()
//            controller = Navigation.findNavController(v)
//            controller.navigate(R.id.action_accountFragment_to_accountSettingFragment)
//        }




//        toolbar.setOnMenuItemClickListener (Toolbar.OnMenuItemClickListener {
//            controller = findNavController()
//            when(it.itemId){
//                R.id.account_setting ->
//                    Toast.makeText(requireActivity(),"ss",Toast.LENGTH_SHORT).show()
//
//
//                R.id.account_alert ->Toast.makeText(requireActivity(),"ss",Toast.LENGTH_SHORT).show()
//                    // controller.navigate(R.id.accountSettingFragment)
//
//            }
//
//            return@OnMenuItemClickListener true
//        })

    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}