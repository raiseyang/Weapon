package com.raise.weapon.aboutme

import androidx.lifecycle.LifecycleOwner
import com.raise.weapon_base_kt.runOnUIThread
import com.raise.weapon_jetpack_kt.AViewModel
import com.raise.weapon_jetpack_kt.LiveDataBus
import com.raise.weapon_ui_kt.toast

class AboutMeViewModel : AViewModel() {

    companion object {
        const val STATE_USER_NAME = 1
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        runOnUIThread(2000) {
            postState(STATE_USER_NAME, "raise")
        }

        LiveDataBus.getChannel("name")
            .observe(this) {
                toast(it as String)
            }
    }

    override fun onCleared() {
        super.onCleared()
        //在这清除数据
    }
}