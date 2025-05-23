package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import woowacourse.shopping.view.inventory.item.toDomain
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.view.util.getParcelableCompat

class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail),
    ProductDetailEventHandler {
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shoppingApplication = application as ShoppingApplication
        val factory =
            ProductDetailViewModel.createFactory(shoppingApplication.shoppingCartRepository2)
        viewModel = ViewModelProvider(this, factory)[ProductDetailViewModel::class.java]

        setSupportActionBar(binding.toolbarProductDetail as Toolbar)
        val cartItem: CartItem =
            intent.getParcelableCompat<InventoryProduct>(KEY_PRODUCT)?.toDomain()?.toCartItem() ?: run {
                onUnexpectedError(getString(R.string.error_product_is_null))
                return
            }
        binding.apply {
            this.cartItem = cartItem
            handler = this@ProductDetailActivity
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_product_detail, menu)
        return true
    }

    override fun onAddToCartSelected(cartItem: CartItem) {
        viewModel.addProduct(cartItem)
        startActivity(ShoppingCartActivity.newIntent(this))
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
