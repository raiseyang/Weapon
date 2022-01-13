package com.raise.weapon_base_kt

import com.raise.weapon_base.LLog

inline fun Any.logd(tag: String? = null, block: () -> String) {
    LLog.d(tag ?: this::class.java.simpleName, block())
}

inline fun Any.logi(tag: String? = null, block: () -> String) {
    LLog.i(tag ?: this::class.java.simpleName, block())
}

inline fun Any.logw(tag: String? = null, block: () -> String) {
    LLog.w(tag ?: this::class.java.simpleName, block())
}

inline fun Any.loge(
    tag: String? = null,
    throwable: Throwable? = null,
    block: () -> String
) {
    if (throwable == null) {
        LLog.e(tag ?: this::class.java.simpleName, block())
    } else {
        LLog.e(tag ?: this::class.java.simpleName, block(), throwable)
    }
}
