package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.DbHelper
import woowacourse.shopping.database.cart.CartItemRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private val binding: ActivityProductDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    private val presenter: ProductDetailContract.Presenter by lazy {
        ProductDetailPresenter(
            this,
            ProductRepositoryImpl,
            CartItemRepositoryImpl(DbHelper.getDbInstance(this), ProductRepositoryImpl),
            RecentlyViewedProductRepositoryImpl(DbHelper.getDbInstance(this), ProductRepositoryImpl)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()

        presenter.onLoadProduct(intent.getLongExtra(PRODUCT_ID, -1))
        initLastViewedProduct()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initLastViewedProduct() {
        presenter.onLoadLastViewedProduct()
    }

    override fun setProduct(product: ProductDetailUIState) {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.ivProductDetail)

        binding.tvProductDetailName.text = product.name
        binding.tvProductDetailPrice.text =
            getString(R.string.product_price).format(PRICE_FORMAT.format(product.price))
        binding.btnProductDetailAdd.setOnClickListener {
            presenter.onAddProductToCart(product.id)
            moveToCartActivity()
        }
    }

    override fun setLastViewedProduct(product: ProductDetailUIState?) {
        if (product == null) {
            binding.layoutLastViewedProduct.isVisible = false
            return
        }
        binding.layoutLastViewedProduct.isVisible = true
        binding.tvLastViewedProductName.text = product.name
        binding.tvLastViewedProductPrice.text = getString(R.string.product_price).format(
            PRICE_FORMAT.format(product.price)
        )
    }

    private fun moveToCartActivity() {
        finish()
        CartActivity.startActivity(this, true)
    }

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"

        fun startActivity(context: Context, productId: Long) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
            context.startActivity(intent)
        }
    }
}
