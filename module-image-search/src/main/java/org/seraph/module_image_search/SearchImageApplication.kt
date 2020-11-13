package org.seraph.module_image_search

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * app初始化
 * date：2019/4/18 14:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
@HiltAndroidApp
class SearchImageApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    @Inject
    lateinit var searchImageAppLike: SearchImageAppLike

    override fun onCreate() {
        super.onCreate()
        //初始化第三方sdk
        searchImageAppLike.onCreate()
    }


}