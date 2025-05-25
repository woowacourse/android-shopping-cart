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
import woowacourse.shopping.presentation.custom.GridSpacingItemDecoration
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.view.ItemCounterListener
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogAdapter
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.detail.DetailFragment

class CatalogFragment :
    BaseFragment<FragmentCatalogBinding>(R.layout.fragment_catalog),
    CatalogAdapter.CatalogEventListener,
    ItemCounterListener {
    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter(eventListener = this) }
    private val viewModel: CatalogViewModel by viewModels { CatalogViewModel.Factory }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initListener()
        setCatalogAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCartState()
    }

    override fun onProductClicked(product: ProductUiModel) {
        navigateToScreen(DetailFragment::class.java, DetailFragment.newBundle(product))
        viewModel.addRecentProduct(product)
    }

    override fun onLoadMoreClicked() {
        viewModel.fetchProducts()
    }

    override fun onInitialAddToCartClicked(product: ProductUiModel) {
        viewModel.initialAddToCart(product)
    }

    override fun increase(productId: Long) {
        viewModel.increaseCartItem(productId)
    }

    override fun decrease(productId: Long) {
        viewModel.decreaseCartItem(productId)
    }

    private fun setCatalogAdapter() {
        binding.recyclerViewProducts.layoutManager =
            GridLayoutManager(requireContext(), SPAN_COUNT).apply {
                spanSizeLookup =
                    object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = catalogAdapter.getItemViewType(position)
                            return when (CatalogItem.CatalogType.entries[viewType]) {
                                CatalogItem.CatalogType.RECENT -> SPAN_COUNT
                                CatalogItem.CatalogType.PRODUCT -> SINGLE_SPAN_COUNT
                                CatalogItem.CatalogType.LOAD_MORE -> SPAN_COUNT
                            }
                        }
                    }
            }

        binding.recyclerViewProducts.addItemDecoration(
            GridSpacingItemDecoration(
                SPAN_COUNT,
                ITEM_SPACING,
            ),
        )
        binding.recyclerViewProducts.adapter = catalogAdapter
    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) { products ->
            catalogAdapter.updateProducts(products)
        }
        viewModel.itemUpdateEvent.observe(viewLifecycleOwner) {
            catalogAdapter.updateItem(it)
        }
        viewModel.totalCartCount.observe(viewLifecycleOwner) {
            if (it == 0) {
                binding.textViewCartTotalAmount.visibility = View.GONE
            } else {
                binding.textViewCartTotalAmount.visibility = View.VISIBLE
            }
            binding.textViewCartTotalAmount.text = it.toString()
        }
    }

    private fun initListener() {
        binding.btnNavigateCart.setOnClickListener {
            navigateToScreen(CartFragment::class.java)
        }
    }

    private fun navigateToScreen(
        fragment: Class<out Fragment>,
        bundle: Bundle? = null,
    ) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.shopping_fragment_container, fragment, bundle)
            addToBackStack(null)
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SINGLE_SPAN_COUNT = 1
        private const val ITEM_SPACING = 12f
    }
}
