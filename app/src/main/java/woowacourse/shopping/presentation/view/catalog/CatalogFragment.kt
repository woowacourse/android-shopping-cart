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
import woowacourse.shopping.presentation.ui.decorations.GridSpacingItemDecoration
import woowacourse.shopping.presentation.ui.layout.QuantitySelectorListener
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogAdapter
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.catalog.event.CatalogMessageEvent
import woowacourse.shopping.presentation.view.detail.DetailFragment

class CatalogFragment :
    BaseFragment<FragmentCatalogBinding>(R.layout.fragment_catalog),
    CatalogAdapter.CatalogEventListener,
    QuantitySelectorListener {
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

    override fun onProductClicked(productId: Long) {
        navigateTo(DetailFragment::class.java, DetailFragment.newBundle(productId))
    }

    override fun onLoadMoreClicked() {
        viewModel.fetchProducts()
    }

    override fun onToggleQuantitySelector(position: Int) {
        catalogAdapter.openQuantitySelectorAt(position)
    }

    override fun increaseQuantity(
        productId: Long,
        position: Int,
    ) {
        viewModel.increaseProductToCart(position, productId)
    }

    override fun decreaseQuantity(
        productId: Long,
        position: Int,
    ) {
        viewModel.decreaseProductFromCart(position, productId)
    }

    private fun initObservers() {
        viewModel.products.observe(viewLifecycleOwner) { items ->
            catalogAdapter.appendProducts(items)
        }

        viewModel.quantityUpdateEvent.observe(viewLifecycleOwner) { (position, quantity) ->
            catalogAdapter.updateQuantityAt(position, quantity)
        }

        viewModel.toastEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                CatalogMessageEvent.FETCH_PRODUCTS_FAILURE ->
                    showToast(R.string.catalog_screen_event_message_fetch_product_failure)

                CatalogMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE ->
                    showToast(R.string.catalog_screen_event_message_increase_cart_item_count_failure)

                CatalogMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE ->
                    showToast(R.string.catalog_screen_event_message_decrease_cart_item_count_failure)

                CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE ->
                    showToast(R.string.catalog_screen_event_message_find_quantity_failure)
            }
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
                    val viewType = catalogAdapter.getItemViewType(position)
                    return when (CatalogItem.CatalogType.entries[viewType]) {
                        CatalogItem.CatalogType.PRODUCT -> SINGLE_SPAN
                        CatalogItem.CatalogType.LOAD_MORE -> SPAN_COUNT
                    }
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
