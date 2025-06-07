package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.view.ActivityResult
import woowacourse.shopping.view.base.BaseActivity

class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail),
    ProductDetailEventHandler {
    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = null

        val productId: Int = intent.getIntExtra(KEY_PRODUCT_ID, 0)
        initializeViewModel(productId)
        binding.viewModel = viewModel
        binding.handler = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initializeViewModel(productId: Int) {
        viewModel.loadInventoryProduct(productId)
        viewModel.loadLastViewedProduct()
    }

    override fun onSelectRecentProduct() {
        with(newIntent(this, viewModel.lastProduct.value?.id ?: 0)) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    override fun onAddToCart() {
        viewModel.addToCart()
        val intent =
            Intent().putExtra(
                ActivityResult.CART_ITEM_ADDED.key,
                viewModel.product.value?.product?.id ?: 0,
            )
        setResult(ActivityResult.CART_ITEM_ADDED.hashCode(), intent)
        finish()
    }

    override fun onDecreaseQuantity() {
        viewModel.decreaseQuantity()
    }

    override fun onIncreaseQuantity() {
        viewModel.increaseQuantity()
    }

    companion object {
        private const val KEY_PRODUCT_ID = "product_id"

        fun newIntent(
            context: Context,
            productId: Int,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).putExtra(
                KEY_PRODUCT_ID,
                productId,
            ).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        }
    }
}
