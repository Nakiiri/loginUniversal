package com.example.demo.Application

import android.app.Application
import cn.leancloud.LCObject
import cn.leancloud.LeanCloud


class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        LeanCloud.initialize(this,
            "mujKHkJlHLti18Xpv50bcgke-gzGzoHsz",
            "tfer9QhshXGl0JEddVU63KU4",
            "https://mujkhkjl.lc-cn-n1-shared.com"
        )


    }
}