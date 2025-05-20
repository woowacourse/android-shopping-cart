package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.ShoppingCartActivityTemplate
import woowacourse.shopping.view.getParcelableCompat
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductDetailActivity :
    ShoppingCartActivityTemplate<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    private val viewModel: ProductDetailViewModel by viewModels()
    private val handler: ProductDetailEventHandler by lazy {
        object : ProductDetailEventHandler {
            override fun onAddToCartSelected(product: Product) {
                viewModel.addProduct(product)
                startActivity(ShoppingCartActivity.newIntent(this@ProductDetailActivity))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarProductDetail as Toolbar)
        val product: Product =
            intent.getParcelableCompat(KEY_PRODUCT) ?: run {
                onUnexpectedError(getString(R.string.error_product_is_null))
                return
            }
        viewModel.setProduct(product)
        binding.apply {
            viewModel = this@ProductDetailActivity.viewModel
            handler = this@ProductDetailActivity.handler
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_close) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val KEY_PRODUCT = "product"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }
}

interface ProductDetailEventHandler {
    fun onAddToCartSelected(product: Product)
}
