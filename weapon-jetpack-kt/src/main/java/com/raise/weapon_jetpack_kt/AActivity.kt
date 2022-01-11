package com.raise.weapon_jetpack_kt

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class AActivity<VM : AViewModel>(private val vmClass: Class<VM>) : AppCompatActivity() {

    // 持有一个viewModel类，通过传入参数的Class构造
    private lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initViewModel()
        initView()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 构造viewModel
     * 绑定lifeCycle
     */
    open fun initViewModel() {
        viewModel = ViewModelProvider(this)[vmClass]
        lifecycle.addObserver(viewModel)
    }

    abstract fun initView()

    /**
     * 便于观察viewModel状态数据的语法糖
     */
    fun <T> observe(state: Int, observer: Observer<T>) {
        viewModel.observe(this, state, observer)
    }

}