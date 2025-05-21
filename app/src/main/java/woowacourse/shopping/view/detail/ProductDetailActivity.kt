package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.ActivityBoilerPlateCode
import woowacourse.shopping.view.base.ActivityBoilerPlateCodeImpl
import woowacourse.shopping.view.base.QuantitySelectorEventHandler
import woowacourse.shopping.view.getParcelableCompat
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductDetailActivity :
    AppCompatActivity(),
    ActivityBoilerPlateCode<ActivityProductDetailBinding> by ActivityBoilerPlateCodeImpl(
        R.layout.activity_product_detail,
    ) {
    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setSupportActionBar(binding.toolbarProductDetail as Toolbar)
        val product: Product =
            intent.getParcelableCompat(KEY_PRODUCT) ?: run {
                onUnexpectedError(getString(R.string.error_product_is_null))
                return
            }
        viewModel.setProduct(product)
        binding.apply {
            handler = ProductDetailEventHandlerImpl()
            viewModel = this@ProductDetailActivity.viewModel
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

    private inner class ProductDetailEventHandlerImpl : ProductDetailEventHandler {
        override fun onQuantityMinusSelected(quantity: MutableLiveData<Int>) {
            quantity.value = quantity.value?.minus(1)
        }

        override fun onQuantityPlusSelected(quantity: MutableLiveData<Int>) {
            quantity.value = quantity.value?.plus(1)
        }

        override fun onAddToCartSelected(product: Product) {
            viewModel.addProduct(product)
            startActivity(ShoppingCartActivity.newIntent(this@ProductDetailActivity))
        }
    }
}

interface ProductDetailEventHandler : QuantitySelectorEventHandler {
    fun onAddToCartSelected(product: Product)
}
