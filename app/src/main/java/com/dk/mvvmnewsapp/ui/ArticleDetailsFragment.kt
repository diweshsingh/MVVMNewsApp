package com.dk.mvvmnewsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.dk.mvvmnewsapp.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * This Fragment will be responsible for displaying the article in webview.
 * Article URL will be sent through bundle, in case of no url sent, https:/www.google.com will be loaded
 */
@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private lateinit var articleUrl: String
    private var _binding: FragmentArticleDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleUrl = arguments?.getString("articleUrl")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWebView()
    }

    /**
     * This method will load the URL in webview, until unless URL is not loaded in webview loading indicator will be shown.
     */
    private fun setWebView() {

        binding.apply {
            // setting up the URL to webview
            webview.loadUrl(articleUrl)
            // Attaching a webview client so that whenever URL will be fully loaded into webview, It will be notfied
            // In meantime loading indicator will be shown to user.
            webview.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    // do your stuff here
                    progressView.root.visibility = View.GONE
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}