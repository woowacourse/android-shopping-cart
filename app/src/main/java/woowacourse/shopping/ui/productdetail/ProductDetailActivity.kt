package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.common.DataBindingActivity
import woowacourse.shopping.ui.custom.CartCountView
import woowacourse.shopping.ui.model.ActivityResult

class ProductDetailActivity : DataBindingActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModel.Factory }
    private val productId: Int by lazy { intent.getIntExtra(KEY_PRODUCT_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        removeSupportActionBarTitle()
        initViewBinding()
        initObservers()
        initCartQuantityView()
        handleFromProducts()
        viewModel.loadProductDetail(productId)
        viewModel.addHistoryProduct(productId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_product_detail_close) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun removeSupportActionBarTitle() {
        supportActionBar?.title = null
    }

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.onClickHandler =
            object : OnClickHandler {
                override fun onAddCartProductClick() {
                    viewModel.updateCartProduct()
                }

                override fun onLastHistoryProductClick(id: Int) {
                    setResult(
                        ActivityResult.PRODUCT_DETAIL_HISTORY_PRODUCT_CLICKED.code,
                        Intent().apply {
                            putExtra(ActivityResult.PRODUCT_DETAIL_HISTORY_PRODUCT_CLICKED.key, id)
                        },
                    )
                    finish()
                }
            }
    }

    private fun initObservers() {
        viewModel.catalogProduct.observe(this) { cartProduct ->
            binding.productDetailCartProductCount.setCount(cartProduct.quantity)
        }
        viewModel.onCartProductAddSuccess.observe(this) { isSuccess ->
            isSuccess?.let { handleCartProductAddResult(it) }
        }
    }

    private fun handleCartProductAddResult(isSuccess: Boolean) {
        if (isSuccess) {
            setResult(
                ActivityResult.PRODUCT_DETAIL_CART_UPDATED.code,
                Intent().apply {
                    putExtra(ActivityResult.PRODUCT_DETAIL_CART_UPDATED.key, productId)
                },
            )
            Toast.makeText(this, getString(R.string.product_detail_cart_add_success), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initCartQuantityView() {
        binding.productDetailCartProductCount.setOnClickHandler(
            object : CartCountView.OnClickHandler {
                override fun onIncreaseClick() {
                    viewModel.increaseCartProductQuantity()
                }

                override fun onDecreaseClick() {
                    viewModel.decreaseCartProductQuantity()
                }
            },
        )
    }

    private fun handleFromProducts() {
        intent.getBooleanExtra(KEY_IS_FROM_HOME, false).let { isRecentProductShown ->
            if (isRecentProductShown) viewModel.loadLastHistoryProduct()
        }
    }

    interface OnClickHandler {
        fun onAddCartProductClick()

        fun onLastHistoryProductClick(id: Int)
    }

    companion object {
        private const val KEY_PRODUCT_ID = "PRODUCT_ID"
        private const val KEY_IS_FROM_HOME = "IS_FROM_PRODUCTS"

        fun newIntent(
            context: Context,
            id: Int,
            isRecentHistoryProductShown: Boolean = true,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT_ID, id)
                putExtra(KEY_IS_FROM_HOME, isRecentHistoryProductShown)
            }
    }
}
