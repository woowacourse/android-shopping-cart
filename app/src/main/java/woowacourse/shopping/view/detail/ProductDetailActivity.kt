package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail),
    ProductDetailEventHandler {
    private lateinit var viewModel: ProductDetailViewModel

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
        val application = application as ShoppingApplication
        val factory =
            ProductDetailViewModel.createFactory(
                application.inventoryRepository,
                application.shoppingCartRepository,
                application.recentProductRepository,
            )
        viewModel = ViewModelProvider(this, factory)[ProductDetailViewModel::class.java]
        viewModel.loadInventoryProduct(productId)
        viewModel.loadRecentProduct(productId)
    }

    override fun onSelectRecentProduct() {
        with(newIntent(this, viewModel.lastProduct.value?.id ?: 0)) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    override fun onAddToCart() {
        viewModel.addToCart()
        startActivity(ShoppingCartActivity.newIntent(this))
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
            )
        }
    }
}
