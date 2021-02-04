package com.raise.weapon

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raise.weapon_base.TimeUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimeUtilTest {

    @Test
    fun convertToHsm() {
        val _59s = TimeUtil.convertToHsm(59 * 1000)
        println("_59s=$_59s")
        Assert.assertEquals(_59s, "0h0m59s")

        val _50m59s = TimeUtil.convertToHsm(59 * 1000 + 50 * 60 * 1000)
        println("_50m59s=$_50m59s")
        Assert.assertEquals(_50m59s, "0h50m59s")

        val _111h50m59s = TimeUtil.convertToHsm(59 * 1000 + 50 * 60 * 1000 + 111L * 60 * 60 * 1000)
        println("_111h50m59s=$_111h50m59s")
        Assert.assertEquals(_111h50m59s, "111h50m59s")
    }
}