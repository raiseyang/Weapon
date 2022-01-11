package com.raise.weapon_jetpack_kt

import androidx.lifecycle.MutableLiveData

object LiveDataBus {

    private val bus: MutableMap<String, MutableLiveData<Any>> = mutableMapOf()

    fun getChannel(channelName: String): MutableLiveData<Any> {
        if (bus[channelName] == null) {
            bus[channelName] = MutableLiveData<Any>()
        }
        return bus[channelName]!!
    }

}