package com.blend.enlive.main

import androidx.databinding.ObservableInt
import com.blend.base.common.BaseViewModel
import com.blend.enlive.R
import com.blend.enlive.main.tab.MAIN_TAB_ACCOUNT
import com.blend.enlive.main.tab.MAIN_TAB_HOME
import com.blend.enlive.main.tab.MAIN_TAB_MINE
import com.blend.enlive.main.tab.MAIN_TAB_PROJECT


class MainViewModel : BaseViewModel() {

    val currentItem: ObservableInt = ObservableInt(MAIN_TAB_HOME)

    val onItemSelected: (Int) -> Boolean = { itemId ->
        val targetPosition = when (itemId) {
            R.id.menu_home -> {
                MAIN_TAB_HOME
            }
            R.id.menu_account -> {
                MAIN_TAB_ACCOUNT
            }
            R.id.menu_project -> {
                MAIN_TAB_PROJECT
            }
            R.id.menu_mine -> {
                MAIN_TAB_MINE
            }
            else -> {
                MAIN_TAB_HOME
            }
        }
        if (currentItem.get() != targetPosition) {
            currentItem.set(targetPosition)
        }
        true

    }

}