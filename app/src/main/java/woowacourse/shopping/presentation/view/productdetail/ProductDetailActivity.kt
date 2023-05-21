package woowacourse.shopping.presentation.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.respository.cart.CartRepositoryImpl
import woowacourse.shopping.data.respository.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.util.showToast

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding

    private val presenter: ProductDetailContract.Presenter by lazy {
        ProductDetailPresenter(
            this,
            productRepository = ProductRepositoryImpl(CartDao(this)),
            cartRepository = CartRepositoryImpl(CartDao(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        supportActionBar?.title = ACTION_BAR_TITLE

        val productId = intent.getLongExtra(KEY_PRODUCT_ID, -1)
        val recentProductId = intent.getLongExtra(KEY_RECENT_PRODUCT_ID, -1)
        presenter.loadProductInfoById(productId)
        setRecentProductView(recentProductId)


        setAddCartClickListener(productId)
    }

    private fun setRecentProductView(recentProductId: Long) {
        if (recentProductId == -1L) {
            binding.clRecentProduct.visibility = View.GONE
        } else {
            presenter.loadRecentProductById(recentProductId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_close -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showProductInfoView(productModel: ProductModel) {
        binding.productItem = productModel
    }

    override fun showRecentProductById(productModel: ProductModel) {
        binding.recentProductItem = productModel
    }

    private fun setAddCartClickListener(productId: Long) {
        binding.btProductDetailAddToCart.setOnClickListener {
            presenter.addCart(productId)
        }
    }

    override fun addCartSuccessView() {
        showToast(getString(R.string.toast_message_success_add_cart))
        finish()
    }

    override fun handleErrorView() {
        showToast(getString(R.string.toast_message_system_error))
        finish()
    }

    companion object {
        private const val KEY_PRODUCT_ID = "KEY_PRODUCT_ID"
        private const val KEY_RECENT_PRODUCT_ID = "KEY_RECENT_PRODUCT_ID"
        private const val ACTION_BAR_TITLE = ""

        internal fun createIntent(
            context: Context,
            productId: Long,
            recentProductId: Long = -1L
        ): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(KEY_PRODUCT_ID, productId)
            intent.putExtra(KEY_RECENT_PRODUCT_ID, recentProductId)

            return intent
        }
    }
}
