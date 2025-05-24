package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.view.util.getParcelableCompat

class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail),
    ProductDetailEventHandler {
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarProductDetail as Toolbar)

        initializeViewModel()
        val product: InventoryProduct =
            intent.getParcelableCompat<InventoryProduct>(KEY_PRODUCT) ?: run {
                onUnexpectedError(getString(R.string.error_product_is_null))
                return
            }
        binding.apply {
            this.product = product
            handler = this@ProductDetailActivity
        }
    }

    private fun initializeViewModel() {
        val shoppingApplication = application as ShoppingApplication
        val factory =
            ProductDetailViewModel.createFactory(
                shoppingApplication.inventoryRepository,
                shoppingApplication.shoppingCartRepository,
            )
        viewModel = ViewModelProvider(this, factory)[ProductDetailViewModel::class.java]
        viewModel.quantity.observe(this) { quantity ->
            binding.tvQuantity.text = quantity.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_product_detail, menu)
        return true
    }

    override fun onAddToCart(product: InventoryProduct) {
        viewModel.addToCart(product)
        startActivity(ShoppingCartActivity.newIntent(this))
    }

    override fun onDecreaseQuantity() {
        viewModel.decreaseQuantity()
    }

    override fun onIncreaseQuantity() {
        viewModel.increaseQuantity()
    }

    companion object {
        private const val KEY_PRODUCT = "product"

        fun newIntent(
            context: Context,
            product: InventoryProduct,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).putExtra(KEY_PRODUCT, product)
        }
    }
}
