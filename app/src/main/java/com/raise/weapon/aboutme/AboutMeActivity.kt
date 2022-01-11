package com.raise.weapon.aboutme

import com.raise.weapon.R
import com.raise.weapon_jetpack_kt.AActivity
import com.raise.weapon_ui_kt.toast

class AboutMeActivity : AActivity<AboutMeViewModel>(AboutMeViewModel::class.java) {

    override fun getLayoutId(): Int = R.layout.activity_about_me

    override fun initViewModel() {
        super.initViewModel()
        observe<String>(AboutMeViewModel.STATE_USER_NAME) {
            toast("name=$it")
        }

    }

    override fun initView() {

    }

}