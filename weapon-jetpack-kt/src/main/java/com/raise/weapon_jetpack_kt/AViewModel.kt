package com.raise.weapon_jetpack_kt

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.raise.weapon_base.LLog
import com.raise.weapon_base.UIThreadUtil
import com.raise.weapon_base_kt.runOnUIThread

abstract class AViewModel : ViewModel(), DefaultLifecycleObserver, LifecycleOwner {

    val printLog = true

    /**
     * 将ViewModel实现成为LifecycleOwner
     * 便于Model层、LiveDataBus可以监听ViewModel
     */
    @SuppressLint("StaticFieldLeak")
    private val lifecycleRegistry = LifecycleRegistry(this)

    private val _sign = HashMap<Int, MutableLiveData<*>>()

    /**
     * 监听业务数据
     * @param state 代表不同的业务态
     */
    fun <T> observe(lifecycleOwner: LifecycleOwner, state: Int, observer: Observer<T>) {
        // 控制Activity不能监听2次
        if (_sign[state] != null) {
            throw IllegalArgumentException("can't observe state[$state] twice.")
        }
        val liveData = MutableLiveData<T>()
        liveData.observe(lifecycleOwner, observer)
        _sign[state] = liveData
    }

    protected fun postState(state: Int, data: Any) {
        _sign[state]?.let {
            if (UIThreadUtil.isMainThread()) {
                it.value = data
                // 星投影为什么不能作为参数使用
//                it.setValue(data)
//                it.postValue(data)
            } else {
                runOnUIThread { it.value = data }
            }
        }
    }

    private fun printLog(msg: String) {
        if (printLog) {
            LLog.d(this::class.java.simpleName, msg)
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        printLog("onCreate() start.")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart(owner: LifecycleOwner) {
        printLog("onStart() start.")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onResume(owner: LifecycleOwner) {
        printLog("onResume() start.")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause(owner: LifecycleOwner) {
        printLog("onPause() start.")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onStop(owner: LifecycleOwner) {
        printLog("onStop() start.")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        printLog("onDestroy() start.")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}