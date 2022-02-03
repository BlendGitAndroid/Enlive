package com.blend.enlive.project

import android.os.Bundle
import com.blend.base.common.ui.BaseFragment
import com.blend.enlive.R
import com.blend.enlive.databinding.FragmentProjectBinding


class ProjectFragment : BaseFragment<ProjectViewModel, FragmentProjectBinding>() {

    override fun layoutId(): Int {
        return R.layout.fragment_project
    }

    override fun initView(savedInstanceState: Bundle?) {

    }


}