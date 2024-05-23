package woowacourse.shopping.presentation.ui.shopping

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shopping.adapter.ProductListAdapter
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingViewType
import woowacourse.shopping.presentation.util.EventObserver

class ShoppingActivity : BindingActivity<ActivityShoppingBinding>(), ShoppingHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_shopping

    private val viewModel: ShoppingViewModel by viewModels { ViewModelFactory() }

    private val adapter: ProductListAdapter = ProductListAdapter(this)

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                handleActivityResult(activityResult.data)
            }
        }

    private fun handleActivityResult(data: Intent?) {
        data?.let {
            updateSingleProductQuantity(it)
            updateMultipleProductsQuantities(it)
        }
    }

    private fun updateSingleProductQuantity(intent: Intent) {
        val modifiedProductId = intent.getLongExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, -1L)
        val newQuantity = intent.getIntExtra(ProductDetailActivity.EXTRA_NEW_PRODUCT_QUANTITY, -1)
        if (modifiedProductId != -1L && newQuantity != -1) {
            viewModel.updateProductQuantity(modifiedProductId, newQuantity)
        }
    }

    private fun updateMultipleProductsQuantities(intent: Intent) {
        val modifiedProductIds = intent.getLongArrayExtra(CartActivity.EXTRA_CHANGED_PRODUCT_IDS)
        val newQuantities = intent.getIntArrayExtra(CartActivity.EXTRA_NEW_PRODUCT_QUANTITIES)
        modifiedProductIds?.zip(newQuantities?.toList() ?: emptyList())?.forEach { (id, quantity) ->
            viewModel.updateProductQuantity(id, quantity)
        }
    }

    override fun initStartView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.loadInitialShoppingItems()
        }
        initAdapter()
        observeRecentProductUpdates()
        observeProductUpdates()
        observeErrorEventUpdates()
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(this, GRIDLAYOUT_COL)
        layoutManager.spanSizeLookup = getSpanManager()
        binding.rvShopping.layoutManager = layoutManager
        binding.rvShopping.adapter = adapter
    }

    private fun getSpanManager() =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewTypeValue = adapter.getItemViewType(position)
                return when (ShoppingViewType.of(viewTypeValue)) {
                    ShoppingViewType.RecentProduct -> ShoppingViewType.RecentProduct.span
                    ShoppingViewType.Product -> ShoppingViewType.Product.span
                    ShoppingViewType.LoadMore -> ShoppingViewType.LoadMore.span
                }
            }
        }

    private fun observeRecentProductUpdates() {
        viewModel.recentProducts.observe(this) { state ->
            when (state) {
                is UiState.Success ->
                    adapter.insertRecentProductItems(
                        ProductListItem.RecentProductItems(state.data),
                    )

                UiState.None -> {}
            }
        }
    }

    private fun observeProductUpdates() {
        viewModel.shoppingProducts.observe(this) { state ->
            when (state) {
                is UiState.Success -> adapter.updateProductItems(state.data)
                UiState.None -> {}
            }
        }
    }

    private fun observeErrorEventUpdates() {
        viewModel.error.observe(
            this,
            EventObserver {
                showToast(it.message)
            },
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        CartActivity.startWithResult(this, activityResultLauncher)
        return true
    }

    override fun onProductItemClick(productId: Long) {
        ProductDetailActivity.startWithResult(this, activityResultLauncher, productId)
    }

    override fun onLoadMoreClick() {
        viewModel.fetchProductForNewPage()
    }

    override fun onQuantityControlClick(
        item: ProductListItem.ShoppingProductItem?,
        quantityDelta: Int,
    ) {
        item?.let {
            viewModel.updateCartItemQuantity(
                item.toProduct(),
                quantityDelta,
            )
        }
    }

    companion object {
        const val GRIDLAYOUT_COL = 2
    }
}
