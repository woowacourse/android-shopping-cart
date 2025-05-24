package woowacourse.shopping.presentation.view.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentCatalogBinding
import woowacourse.shopping.databinding.MenuItemCartBinding
import woowacourse.shopping.presentation.base.BaseFragment
import woowacourse.shopping.presentation.model.CatalogItem
import woowacourse.shopping.presentation.ui.decorations.GridSpacingItemDecoration
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogAdapter
import woowacourse.shopping.presentation.view.catalog.event.CatalogMessageEvent
import woowacourse.shopping.presentation.view.detail.DetailFragment

class CatalogFragment :
    BaseFragment<FragmentCatalogBinding>(R.layout.fragment_catalog),
    CatalogAdapter.CatalogEventListener {
    private val viewModel: CatalogViewModel by viewModels { CatalogViewModel.Factory }
    private val catalogAdapter: CatalogAdapter by lazy { CatalogAdapter(eventListener = this) }

    private var _toolbarBinding: MenuItemCartBinding? = null
    private val toolbarBinding: MenuItemCartBinding get() = _toolbarBinding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListener()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()

        viewModel.refreshProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _toolbarBinding = null
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
        setupRecyclerView()
        setupCartToolbarMenu()
    }

    private fun setupListener() {
        toolbarBinding.ivShoppingCart.setOnClickListener {
            navigateTo(CartFragment::class.java)
        }
    }

    private fun setupCartToolbarMenu() {
        binding.toolbarCatalog.inflateMenu(R.menu.toolbar_menu_cart)
        val menuItem = binding.toolbarCatalog.menu.findItem(R.id.action_cart)

        _toolbarBinding = MenuItemCartBinding.inflate(LayoutInflater.from(requireContext()))
        menuItem.actionView = toolbarBinding.root

        toolbarBinding.vm = viewModel
        toolbarBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = catalogAdapter.getSpanSizeAt(position)
            }

        binding.recyclerViewProducts.apply {
            itemAnimator = null
            this.layoutManager = layoutManager

            addItemDecoration(
                GridSpacingItemDecoration(
                    SPAN_COUNT,
                    ITEM_SPACING_DP,
                    EDGE_SPACING_DP,
                    CatalogItem.CatalogType.PRODUCT.ordinal,
                ),
            )
            adapter = catalogAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) {
            catalogAdapter.submitList(it)
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

            CatalogMessageEvent.FETCH_CART_ITEM_COUNT_FAILURE ->
                R.string.catalog_screen_event_message_fetch_cart_item_count_failure

            CatalogMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE ->
                R.string.catalog_screen_event_message_increase_cart_item_count_failure

            CatalogMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE ->
                R.string.catalog_screen_event_message_decrease_cart_item_count_failure

            CatalogMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE ->
                R.string.catalog_screen_event_message_find_quantity_failure

            CatalogMessageEvent.FETCH_RECENT_PRODUCT_FAILURE ->
                R.string.catalog_screen_event_message_fetch_recent_product_failure
        }

    private fun CatalogAdapter.getSpanSizeAt(position: Int): Int =
        when (CatalogItem.CatalogType.entries[getItemViewType(position)]) {
            CatalogItem.CatalogType.PRODUCT -> SINGLE_SPAN
            CatalogItem.CatalogType.RECENT_PRODUCT -> SPAN_COUNT
            CatalogItem.CatalogType.LOAD_MORE -> SPAN_COUNT
        }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SINGLE_SPAN = 1
        private const val ITEM_SPACING_DP = 8f
        private const val EDGE_SPACING_DP = 20f
    }
}
