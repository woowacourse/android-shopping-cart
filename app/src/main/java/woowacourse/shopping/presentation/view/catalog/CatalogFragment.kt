package woowacourse.shopping.presentation.view.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentCatalogBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.ui.decorations.GridSpacingItemDecoration
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogAdapter
import woowacourse.shopping.presentation.view.detail.DetailFragment

class CatalogFragment :
    BaseFragment<FragmentCatalogBinding>(R.layout.fragment_catalog),
    CatalogAdapter.CatalogEventListener {
    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter(eventListener = this) }
    private val viewModel: CatalogViewModel by viewModels { CatalogViewModel.Factory }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListener()
        initRecyclerView()
    }

    override fun onProductClicked(product: ProductUiModel) {
        navigateTo(DetailFragment::class.java, DetailFragment.newBundle(product.id))
    }

    override fun onLoadMoreClicked() {
        viewModel.fetchProducts()
    }

    private fun initObservers() {
        viewModel.products.observe(viewLifecycleOwner) { (items, hasMore) ->
            catalogAdapter.updateProducts(items, hasMore)
        }
    }

    private fun initListener() {
        binding.btnNavigateCart.setOnClickListener {
            navigateTo(CartFragment::class.java)
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val isLastPosition = position == catalogAdapter.itemCount - SINGLE_SPAN
                    return if (isLastPosition && catalogAdapter.hasMore) SPAN_COUNT else SINGLE_SPAN
                }
            }

        binding.recyclerViewProducts.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, ITEM_SPACING_DP))
            adapter = catalogAdapter
        }
    }

    private fun navigateTo(
        fragmentClass: Class<out Fragment>,
        bundle: Bundle? = null,
    ) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.shopping_fragment_container, fragmentClass, bundle)
            addToBackStack(null)
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SINGLE_SPAN = 1
        private const val ITEM_SPACING_DP = 12f
    }
}
