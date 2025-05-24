package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.view.base.ActivityBoilerPlateCode
import woowacourse.shopping.view.base.ActivityBoilerPlateCodeImpl
import woowacourse.shopping.view.base.QuantitySelectorEventHandler
import woowacourse.shopping.view.getParcelableCompat
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.view.uimodel.ProductUiModel

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
        val productUiModel: ProductUiModel =
            intent.getParcelableCompat(KEY_PRODUCT) ?: run {
                onUnexpectedError(getString(R.string.error_product_is_null))
                return
            }
        viewModel.setProduct(productUiModel)
        binding.apply {
            handler = ProductDetailEventHandlerImpl()
            viewModel = this@ProductDetailActivity.viewModel
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.addRecentProduct()
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
            productUiModel: ProductUiModel,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, productUiModel)
            }
        }
    }

    private inner class ProductDetailEventHandlerImpl : ProductDetailEventHandler {
        override fun onAddToCartSelected(productUiModel: ProductUiModel) {
            viewModel.addProduct(productUiModel)
            startActivity(ShoppingCartActivity.newIntent(this@ProductDetailActivity))
        }
    }
}

interface ProductDetailEventHandler : QuantitySelectorEventHandler {
    fun onAddToCartSelected(productUiModel: ProductUiModel)
}
