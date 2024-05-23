package woowacourse.shopping.presentation.ui.shopping

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
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

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            processActivityResult(activityResult)
        }

    private fun processActivityResult(activityResult: ActivityResult) {
        val isResultOkAndHasData =
            activityResult.data != null && activityResult.resultCode == RESULT_OK
        if (isResultOkAndHasData) updateCartItemQuantity(activityResult)
    }

    private fun updateCartItemQuantity(activityResult: ActivityResult) {
        val modifiedProductId =
            activityResult.data!!.getLongExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, -1L)
        if (modifiedProductId != -1L) {
            viewModel.modifyShoppingProductQuantity(modifiedProductId, 1)
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
        CartActivity.start(this)
        return true
    }

    override fun onProductItemClick(productId: Long) {
        ProductDetailActivity.startWithResult(this, activityLauncher, productId)
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

    override fun onResume() {
        super.onResume()
        viewModel.cartProducts
    }

    companion object {
        const val GRIDLAYOUT_COL = 2
    }
}
