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
import woowacourse.shopping.presentation.view.productdetail.dialog.ProductDetailDialog
import woowacourse.shopping.presentation.view.util.showToast

class ProductDetailActivity :
    AppCompatActivity(),
    ProductDetailContract.View,
    ProductDetailDialog.ProductDetailDialogListener {
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
        binding.clRecentProduct.setOnClickListener {

            val intent = createIntent(this, recentProductId)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            setResult(RESULT_OK, this.intent)
            startActivity(intent)
        }
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

    override fun showProductInfoView(product: ProductModel) {
        binding.productItem = product
        setAddCartClickListener(product)
    }

    override fun showRecentProductById(product: ProductModel) {
        binding.recentProductItem = product
    }

    private fun setAddCartClickListener(product: ProductModel) {
        binding.btProductDetailAddToCart.setOnClickListener {
            val productDetailDialog = ProductDetailDialog.newInstance(product)
            productDetailDialog.setEventListener(this)
            productDetailDialog.show(supportFragmentManager, ProductDetailDialog.TAG)
        }
    }

    override fun addCartSuccessView() {
        showToast(getString(R.string.toast_message_success_add_cart))
        setResult(RESULT_OK)
        finish()
    }

    override fun handleErrorView() {
        showToast(getString(R.string.toast_message_system_error))
        finish()
    }

    override fun onOrderClick(productId: Long, count: Int) {
        presenter.addCart(productId, count)
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
            return Intent(context, ProductDetailActivity::class.java)
                .putExtra(KEY_PRODUCT_ID, productId)
                .putExtra(KEY_RECENT_PRODUCT_ID, recentProductId)
        }
    }
}
