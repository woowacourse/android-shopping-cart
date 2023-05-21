package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private val presenter: ProductDetailContract.Presenter by lazy {
        ProductDetailPresenter(
            this,
            ProductRepositoryImpl,
            CartRepositoryImpl(this, ProductRepositoryImpl),
            RecentlyViewedProductRepositoryImpl(this, ProductRepositoryImpl),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        val productId = intent.getLongExtra(PRODUCT_ID, -1)
        presenter.loadProduct(productId)
        presenter.showLastlyViewedProduct(productId)
        presenter.addRecentlyViewedProduct(productId)
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

    override fun setProduct(product: ProductDetailUIState) {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.ivProductDetail)

        binding.detailProduct = product
        binding.btnProductDetailAdd.setOnClickListener {
            // TODO : 초기 수량은 1, 추후에 수량 결정할 버튼 추가 예정
            presenter.addProductToCart(product.id, 1)
            moveToCartActivity()
        }
    }

    override fun showLastlyViewedProduct(product: RecentlyViewedProductUIState) {
        binding.recentProduct = product
    }

    override fun hideLastlyViewedProduct() {
        binding.layoutLastlyViewed.isVisible = false
    }

    override fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error_not_found), Toast.LENGTH_SHORT).show()
    }

    private fun moveToCartActivity() {
        finish()
        CartActivity.startActivity(this)
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
