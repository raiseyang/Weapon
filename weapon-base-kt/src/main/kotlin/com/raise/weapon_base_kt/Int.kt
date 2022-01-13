package com.raise.weapon_base_kt

/**
 *
 */
fun Int.toPercent(total: Int): Int =
    if (this > Int.MAX_VALUE / 100) {
        (this.toLong() * 100 / total.toLong()).toInt()
    } else {
        this * 100 / total
    }