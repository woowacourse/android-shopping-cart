package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.utils.getSerializable
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.datasource.dao.CartDao
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private val shoppingDBOpenHelper: ShoppingDBOpenHelper by lazy {
        ShoppingDBOpenHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateExtras()
        initBinding()
        initToolbar()
        initProductDetailCartButton()
        initPresenter()
    }

    override fun initRecentProduct(recentProduct: ProductModel?) {
        binding.recentProduct = recentProduct
    }

    override fun updateProductDetail(product: ProductModel) {
        binding.product = product
    }

    override fun showCart() {
        startCartActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_detail_close_action -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateExtras() {
        intent.extras?.containsKey(PRODUCT_EXTRA_NAME) ?: return finish()
    }

    private fun initBinding() {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.productDetailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initPresenter() {
        val product = intent.getSerializable<ProductModel>(PRODUCT_EXTRA_NAME) ?: return
        val recentProduct =
            intent.getSerializable<ProductModel>(RECENT_PRODUCT_EXTRA_NAME) ?: return
        presenter = ProductDetailPresenter(
            this,
            product = product,
            recentProduct = recentProduct,
            cartRepository = CartRepository(CartDao(shoppingDBOpenHelper.writableDatabase))
        )
    }

    private fun startCartActivity() {
        val intent = CartActivity.createIntent(this)
        startActivity(intent)
    }

    private fun initProductDetailCartButton() {
        binding.productDetailCartButton.setOnClickListener {
            presenter.addToCart()
        }
    }

    companion object {
        private const val PRODUCT_EXTRA_NAME = "product"
        private const val RECENT_PRODUCT_EXTRA_NAME = "recentProduct"

        fun createIntent(
            context: Context,
            product: ProductModel,
            recentProduct: ProductModel?
        ): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT_EXTRA_NAME, product)
            intent.putExtra(RECENT_PRODUCT_EXTRA_NAME, recentProduct)
            return intent
        }
    }
}
