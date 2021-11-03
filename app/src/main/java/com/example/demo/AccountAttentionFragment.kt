package com.example.demo

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountAttentionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountAttentionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var attentionTablayout:TabLayout
    private lateinit var attentionViewpager2:ViewPager2
    private lateinit var toolbar: Toolbar
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayout2: LinearLayout

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
        return inflater.inflate(R.layout.fragment_account_attention, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        attentionTablayout = requireActivity().findViewById(R.id.attention_tablayout)
        attentionViewpager2 = requireActivity().findViewById(R.id.attention_viewpager2)
        toolbar = requireActivity().findViewById(R.id.toolbar_myAttention)
        linearLayout = requireActivity().findViewById(R.id.linearLayout)
        linearLayout2 = requireActivity().findViewById(R.id.linearLayout2)

        val anim = AnimationUtils.loadAnimation(requireActivity(),R.anim.slide_to_right)

        attentionViewpager2.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0->AttentionUserFragment()
                    else->AttentionTopicFragment()
                }
            }
        }

        TabLayoutMediator(attentionTablayout,attentionViewpager2){tab,position ->
            when(position){
                0->tab.text = getString(R.string.attention_user)
                else->tab.text = getString(R.string.attention_topic)
            }
        }.attach()

        toolbar.setNavigationOnClickListener { v->
            linearLayout.startAnimation(anim)
            linearLayout2.startAnimation(anim)
            Handler().postDelayed({
                linearLayout.visibility = View.VISIBLE
                linearLayout2.visibility = View.VISIBLE},
                300)
            Navigation.findNavController(v).navigate(R.id.action_accountAttentionFragment_to_accountFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountAttentionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountAttentionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}