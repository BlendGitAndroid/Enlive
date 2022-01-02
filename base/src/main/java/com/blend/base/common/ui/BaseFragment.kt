package com.blend.base.common.ui

import androidx.viewbinding.ViewBinding
import com.blend.base.common.BaseViewModel
import com.blend.base.common.ui.config.FragmentConfig


abstract class BaseFragment<VM : BaseViewModel,DB : ViewBinding>(val config : FragmentConfig) {



}