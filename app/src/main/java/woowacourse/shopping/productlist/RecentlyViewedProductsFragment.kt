package woowacourse.shopping.productlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.databinding.LayoutRecentlyViewedProductsBinding
import woowacourse.shopping.util.ViewModelFactory

class RecentlyViewedProductsFragment : Fragment(), RecentlyViewedProductsClickAction, LatestViewedProductListener {
    private lateinit var binding: LayoutRecentlyViewedProductsBinding
    private lateinit var adapter: RecentlyViewedProductsAdapter
    private lateinit var recentlyViewedProductsViewModel: RecentlyViewedProductsViewModel
    private var clickListener: RecentlyViewedProductsClickAction? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val viewModelFactory = ViewModelFactory(context.applicationContext)
        recentlyViewedProductsViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(RecentlyViewedProductsViewModel::class.java)
        if (context is RecentlyViewedProductsClickAction) {
            clickListener = context
        } else {
            throw RuntimeException("$context must implement RecentlyViewedProductsClickAction")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutRecentlyViewedProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecentlyViewedProductsAdapter(clickListener!!)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = recentlyViewedProductsViewModel
        binding.recyclerviewRecentlyViewedProducts.adapter = adapter

        recentlyViewedProductsViewModel.productUiModels.observe(viewLifecycleOwner) {
            adapter.submitRecentItems(it)
        }
        recentlyViewedProductsViewModel.lastlyViewedProduct.observe(viewLifecycleOwner) {
            adapter.addRecentItem(it)
        }
        recentlyViewedProductsViewModel.loadRecentlyViewedProducts()
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onRecentProductClicked(id: Long?) {
        id?.let { recentlyViewedProductsViewModel.addRecentlyViewedProduct(id) }
    }

    override fun onUpdateLatestViewedProduct(id: Long) {
        recentlyViewedProductsViewModel.addRecentlyViewedProduct(id)
    }
}
