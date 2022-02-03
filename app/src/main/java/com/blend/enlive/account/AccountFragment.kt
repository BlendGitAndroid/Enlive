package com.blend.enlive.account

import android.os.Bundle
import com.blend.base.common.ui.BaseFragment
import com.blend.enlive.R
import com.blend.enlive.databinding.FragmentAccountBinding


class AccountFragment : BaseFragment<AccountViewHolder, FragmentAccountBinding>() {

    override fun layoutId(): Int {
        return R.layout.fragment_account
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}