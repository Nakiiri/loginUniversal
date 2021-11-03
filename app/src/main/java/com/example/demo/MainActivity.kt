package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.leancloud.LCUser
import com.example.demo.databinding.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.time.toDuration


class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var homeMotionLayout:MotionLayout
    private lateinit var shopMotionLayout:MotionLayout
    private lateinit var verifyMotionLayout:MotionLayout
    private lateinit var accountMotionLayout:MotionLayout
    private lateinit var linearLayout: LinearLayout
    private var lastBackPress = -1L//前一次点击返回键时间

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        homeMotionLayout = findViewById(R.id.HomeMotionLayout)
        shopMotionLayout = findViewById(R.id.ShopMotionLayout)
        verifyMotionLayout = findViewById(R.id.VerifyMotionLayout)
        accountMotionLayout = findViewById(R.id.AccountMotionLayout)
        linearLayout = findViewById(R.id.linearLayout)

        val destinationMap = mapOf(
            R.id.homeFragment to homeMotionLayout,
            R.id.shopFragment to shopMotionLayout,
            R.id.verifyFragment to verifyMotionLayout,
            R.id.accountFragment to accountMotionLayout
        )



//        setupActionBarWithNavController(
//            navController,
//            AppBarConfiguration(destinationMap.keys)
//        )

//        destinationMap.forEach { map->
//            map.value.setOnClickListener { navController.navigate(map.key) }
//        }


//        homeMotionLayout.setOnClickListener { navController.navigate(R.id.homeFragment) }
//        shopMotionLayout.setOnClickListener { navController.navigate(R.id.shopFragment) }
//        verifyMotionLayout.setOnClickListener { navController.navigate(R.id.verifyFragment) }
//        accountMotionLayout.setOnClickListener { navController.navigate(R.id.accountFragment) }
        navController.addOnDestinationChangedListener { controller, destination, _ ->
           // controller.popBackStack()

            destinationMap.values.forEach { it.progress = 0f }
//            homeMotionLayout.progress = 0f
//            shopMotionLayout.progress = 0f
//            verifyMotionLayout.progress = 0f
//            accountMotionLayout.progress = 0f
            destinationMap[destination.id]?.transitionToEnd()
//            when(destination.id){
//                R.id.homeFragment -> homeMotionLayout.transitionToEnd()
//                R.id.shopFragment -> shopMotionLayout.transitionToEnd()
//                R.id.verifyFragment -> verifyMotionLayout.transitionToEnd()
//                else -> accountMotionLayout.transitionToEnd()
//            }
            destinationMap.forEach { map->
                map.value.setOnClickListener {
                    if (map.key!=destination.id){
                        navController.navigate(map.key)
                    }

                }
            }

        }


    }


    //2s内按两次回退键退出
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val currentTime = System.currentTimeMillis()
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if(lastBackPress == -1L || currentTime - lastBackPress >= 2000){
                Toast.makeText(this, getString(R.string.exit_tips), Toast.LENGTH_SHORT).show()
                lastBackPress = currentTime
            }else{
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


//    override fun onSupportNavigateUp() =
//        findNavController(R.id.fragmentContainerView).navigateUp()




}