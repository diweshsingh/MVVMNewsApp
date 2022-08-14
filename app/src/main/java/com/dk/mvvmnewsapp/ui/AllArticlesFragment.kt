package com.dk.mvvmnewsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dk.mvvmnewsapp.R
import com.dk.mvvmnewsapp.adapter.ArticlesAdapter
import com.dk.mvvmnewsapp.databinding.FragmentArticleListBinding
import com.dk.mvvmnewsapp.listener.OnArticleItemClick
import com.dk.mvvmnewsapp.models.Article
import com.dk.mvvmnewsapp.network.ResponseResult
import com.dk.mvvmnewsapp.utils.EspressoIdlingResource
import com.dk.mvvmnewsapp.utils.VerticalSpacingItemDecorator
import com.dk.mvvmnewsapp.viewmodel.AllArticlesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * This fragment is responsible:
 * 1. Call an API to fetch list of articles.
 * 2. Managing the UI for showing the list of article.
 */
@AndroidEntryPoint
class AllArticlesFragment : BaseFragment(), OnArticleItemClick {

    private lateinit var articlesAdapter: ArticlesAdapter

    private var _binding: FragmentArticleListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var  articleViewModel: AllArticlesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: AllArticlesViewModel by viewModels()
        this.articleViewModel = viewModel

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setting up the recycler view.
        initRecycleView()
        // Subscribe the req. observers
        subscribeObserver()
        // Calling the API
        callGetArticleAPI()

    }

    /**
     * Setting up the recycleView
     */
    private fun initRecycleView() {
        val personalProfileDataList: ArrayList<Article> = ArrayList()

        val itemDecorator = VerticalSpacingItemDecorator(2)
        binding.recycleViewOrder.addItemDecoration(itemDecorator)
        binding.recycleViewOrder.setHasFixedSize(true)
        articlesAdapter = ArticlesAdapter(personalProfileDataList, this)
        binding.articlesAdapter = articlesAdapter
    }

    /**
     * Subscribing to all the required livedata
     */
    private fun subscribeObserver() {
        articleViewModel.articleList.onResult { articleList ->
            if (articleList.isNotEmpty()) {

                articlesAdapter.updateData(articleList)
            }
        }


    }

    /**
     * Calling the API to get the Articles
     */
    private fun callGetArticleAPI() {
        binding.apply {
            articleViewModel.getArticlesForCategoryFromAPI(articleCategory = "Tech")
                .onResult { responseResult ->
                    when (responseResult) {
                        is ResponseResult.Success -> {
                            binding.progressView.root.visibility = View.GONE
                            EspressoIdlingResource.decrement()
                            val allArticlesResponse = responseResult.result.data
                            articleViewModel.createArticleList(allArticlesResponse!!.articles)
                        }
                        is ResponseResult.Loading -> {
                            EspressoIdlingResource.increment()
                            progressView.root.visibility = View.VISIBLE

                        }
                        is ResponseResult.Error -> {

                        }

                    }
                }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * This method will be called whenever user will click on any article item/cell.
     */
    override fun onArticleItemClick(article: Article) {
        val bundle = Bundle()
        bundle.putString("articleUrl", article.articleUrl)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)

    }
}