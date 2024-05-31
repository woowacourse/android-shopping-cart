package woowacourse.shopping.presentation.ui.shopping

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

class ShoppingActivity : BindingActivity<ActivityShoppingBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_shopping

    private val viewModel: ShoppingViewModel by viewModels { ViewModelFactory() }

    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(viewModel)
    }

    private val productDetailActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                activityResult.data?.let { handleProductDetailActivityResult(it) }
            }
        }

    private val cartActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                activityResult.data?.let { handleCartActivityResult(it) }
            }
        }

    private fun handleProductDetailActivityResult(intent: Intent) {
        val modifiedProductId = intent.getLongExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, -1L)
        val newQuantity = intent.getIntExtra(ProductDetailActivity.EXTRA_NEW_PRODUCT_QUANTITY, -1)
        if (modifiedProductId != -1L && newQuantity != -1) {
            viewModel.updateProductQuantity(modifiedProductId, newQuantity)
        }
    }

    private fun handleCartActivityResult(intent: Intent) {
        val modifiedProductIds = intent.getLongArrayExtra(CartActivity.EXTRA_CHANGED_PRODUCT_IDS)
        val newQuantities = intent.getIntArrayExtra(CartActivity.EXTRA_NEW_PRODUCT_QUANTITIES)
        if (modifiedProductIds == null || newQuantities == null) return
        viewModel.reloadModifiedItems(modifiedProductIds, newQuantities)
    }

    override fun initStartView(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        if (savedInstanceState == null) {
            viewModel.loadInitialShoppingItems()
        }
        initAdapter()
        observeRecentProductUpdates()
        observeProductUpdates()
        observeErrorEventUpdates()
        observeMoveEvent()
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
                    adapter.updateRecentProductItems(
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

    private fun observeMoveEvent() {
        viewModel.moveEvent.observe(
            this,
            EventObserver {
                when (it) {
                    is FromShoppingToScreen.ProductDetail ->
                        ProductDetailActivity.startWithResultLauncher(
                            this,
                            productDetailActivityResultLauncher,
                            it.productId,
                        )

                    is FromShoppingToScreen.Cart ->
                        CartActivity.startWithResult(
                            this,
                            cartActivityResultLauncher,
                        )
                }
            },
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchInitialRecentProducts()
        viewModel.fetchCartProducts()
    }

    companion object {
        const val GRIDLAYOUT_COL = 2
        private const val EXTRA_PRODUCT_ID = "productId"
        private const val EXTRA_NEW_PRODUCT_QUANTITY = "productQuantity"

        fun startWithNewProductQuantity(
            context: Context,
            productId: Long,
            quantity: Int,
        ) {
            if (context is Activity) {
                Intent(context, ShoppingActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    putExtra(EXTRA_PRODUCT_ID, productId)
                    putExtra(EXTRA_NEW_PRODUCT_QUANTITY, quantity)
                    context.setResult(Activity.RESULT_OK, this)
                    context.startActivity(this)
                }
            } else {
                throw IllegalAccessError("해당 메서드는 액티비티에서 호출해야 합니다")
            }
        }
    }
}
