package com.blend.enlive.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blend.base.common.ui.BaseFragment
import com.blend.enlive.R
import com.blend.enlive.databinding.AppFragmentWebviewBinding


class WebViewFragment : BaseFragment<WebViewViewModel, AppFragmentWebviewBinding>() {

    private val mUrl: String by lazy {
        viewModel.webData.value?.url.orEmpty()
    }

    override fun layoutId(): Int {
        return R.layout.app_fragment_webview
    }

    override fun initView(savedInstanceState: Bundle?) {
        // 配置 WebView
        val webSettings = mBinding.webViewWv.settings
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        @SuppressLint("SetJavaScriptEnabled")
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        mBinding.webViewWv.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        mBinding.webViewWv.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    mBinding.webViewPb.visibility = View.GONE
                } else {
                    if (View.INVISIBLE == mBinding.webViewPb.visibility) {
                        mBinding.webViewPb.visibility = View.VISIBLE
                    }
                    mBinding.webViewPb.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }

        mBinding.webViewWv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }
        }

        // 加载页面
        mBinding.webViewWv.loadUrl(mUrl)
    }

    override fun onResume() {
        super.onResume()
        mBinding.webViewWv.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.webViewWv.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webViewWv.clearCache(true)
        mBinding.webViewWv.removeAllViews()
        mBinding.webViewWv.destroy()
    }


    /** 返回 [WebView] 能否回退上一页 */
    fun canGoBack(): Boolean {
        return if (mBinding.webViewWv.canGoBack()) {
            mBinding.webViewWv.goBack()
            true
        } else {
            false
        }
    }
}