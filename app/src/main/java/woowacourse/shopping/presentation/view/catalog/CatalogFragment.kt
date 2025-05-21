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
import woowacourse.shopping.presentation.ui.layout.QuantityChangeListener
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogAdapter
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem
import woowacourse.shopping.presentation.view.catalog.event.CatalogMessageEvent
import woowacourse.shopping.presentation.view.detail.DetailFragment

class CatalogFragment :
    BaseFragment<FragmentCatalogBinding>(R.layout.fragment_catalog),
    CatalogAdapter.CatalogEventListener,
    QuantityChangeListener {
    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter(eventListener = this) }
    private val viewModel: CatalogViewModel by viewModels { CatalogViewModel.Factory }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        initObserver()
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshProductQuantities()
    }

    override fun onProductClicked(productId: Long) {
        navigateTo(DetailFragment::class.java, DetailFragment.newBundle(productId))
    }

    override fun onLoadMoreClicked() {
        viewModel.loadProducts()
    }

    override fun onQuantitySelectorOpenButtonClicked(productId: Long) {
        viewModel.addProductToCart(productId)
    }

    override fun increaseQuantity(productId: Long) {
        viewModel.addProductToCart(productId)
    }

    override fun decreaseQuantity(productId: Long) {
        viewModel.removeProductFromCart(productId)
    }

    private fun setupUI() {
        setupListeners()
        setupRecyclerView()
    }

    private fun setupListeners() {
        binding.btnNavigateCart.setOnClickListener {
            navigateTo(CartFragment::class.java)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)

        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = catalogAdapter.getSpanSizeAt(position)
            }

        binding.recyclerViewProducts.apply {
            this.layoutManager = layoutManager
            addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, ITEM_SPACING_DP))
            adapter = catalogAdapter
        }
    }

    private fun initObserver() {
        viewModel.products.observe(viewLifecycleOwner) {
            catalogAdapter.appendProducts(it)
        }

        viewModel.quantityUpdateEvent.observe(viewLifecycleOwner) { (productId, quantity) ->
            catalogAdapter.updateQuantityAt(productId, quantity)
        }

        viewModel.toastEvent.observe(viewLifecycleOwner) {
            showToast(it.toMessageResId())
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

    private fun CatalogMessageEvent.toMessageResId(): Int =
        when (this) {
            CatalogMessageEvent.FETCH_PRODUCTS_FAILURE ->
                R.string.catalog_screen_event_message_fetch_product_failure

            CatalogMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE ->
                R.string.catalog_screen_event_message_increase_cart_item_count_failure

            CatalogMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE ->
                R.string.catalog_screen_event_message_decrease_cart_item_count_failure

            CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE ->
                R.string.catalog_screen_event_message_find_quantity_failure
        }

    private fun CatalogAdapter.getSpanSizeAt(position: Int): Int =
        when (CatalogItem.CatalogType.entries[getItemViewType(position)]) {
            CatalogItem.CatalogType.PRODUCT -> SINGLE_SPAN
            CatalogItem.CatalogType.LOAD_MORE -> SPAN_COUNT
        }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SINGLE_SPAN = 1
        private const val ITEM_SPACING_DP = 12f
    }
}
