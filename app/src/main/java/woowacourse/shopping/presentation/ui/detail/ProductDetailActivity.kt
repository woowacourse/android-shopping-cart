package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.shopping.ShoppingActivity
import woowacourse.shopping.presentation.util.EventObserver
import kotlin.properties.Delegates

class ProductDetailActivity : BindingActivity<ActivityProductDetailBinding>(), DetailHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_product_detail

    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory() }

    private var id by Delegates.notNull<Long>()

    override fun initStartView(savedInstanceState: Bundle?) {
        checkIsLastViewedProduct()
        initActionBarTitle()
        fetchInitialData()
        binding.detailHandler = this
        observeLiveDatas()
    }

    private fun checkIsLastViewedProduct() {
        val isLastViewedProduct = intent.getBooleanExtra(EXTRA_IS_LAST_VIEWED_PRODUCT, false)
        if (!isLastViewedProduct) viewModel.loadLastProduct()
    }

    private fun initActionBarTitle() {
        title = getString(R.string.detail_title)
    }

    private fun fetchInitialData() {
        id = intent.getLongExtra(EXTRA_PRODUCT_ID, -1L)
        if (id == -1L) finish()
        viewModel.fetchInitialData(id)
    }

    private fun observeLiveDatas() {
        observeLastProductUpdates()
        observeErrorEventUpdates()
        observeProductsUpdates()
        observeCartEventUpdates()
    }

    private fun observeLastProductUpdates() {
        viewModel.lastProduct.observe(this) {
            when (it) {
                is UiState.None -> {}
                is UiState.Success -> binding.lastProduct = it.data
            }
        }
    }

    private fun observeErrorEventUpdates() {
        viewModel.error.observe(
            this,
            EventObserver { showToast(it.message) },
        )
    }

    private fun observeProductsUpdates() {
        viewModel.shoppingProduct.observe(this) { state ->
            when (state) {
                is UiState.None -> {}
                is UiState.Success -> binding.product = state.data
            }
        }
    }

    private fun observeCartEventUpdates() {
        viewModel.addCartEvent.observe(
            this,
            EventObserver {
                Intent(applicationContext, ShoppingActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    putExtra(EXTRA_PRODUCT_ID, id)
                    putExtra(EXTRA_NEW_PRODUCT_QUANTITY, it)
                    setResult(RESULT_OK, this)
                    startActivity(this)
                }
                finish()
            },
        )
    }

    override fun onAddCartClick() {
        viewModel.saveCartItem()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onQuantityControlClick(
        item: ProductListItem.ShoppingProductItem?,
        quantityDelta: Int,
    ) {
        item?.let {
            viewModel.updateCartItemQuantity(
                quantityDelta,
            )
        }
    }

    override fun onLastProductClick(product: RecentProductItem) {
        startWithFlag(this, product.productId)
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "productId"
        const val EXTRA_NEW_PRODUCT_QUANTITY = "productQuantity"
        private const val EXTRA_IS_LAST_VIEWED_PRODUCT = "isLastViewedProduct"

        fun startWithFlag(
            context: Context,
            productId: Long,
        ) {
            Intent(context, ProductDetailActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(EXTRA_PRODUCT_ID, productId)
                putExtra(EXTRA_IS_LAST_VIEWED_PRODUCT, true)
                context.startActivity(this)
            }
        }

        fun startWithResult(
            context: Context,
            activityLauncher: ActivityResultLauncher<Intent>,
            productId: Long,
        ) {
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                activityLauncher.launch(this)
            }
        }
    }
}
