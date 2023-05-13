package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.common.data.dao.CartDao
import woowacourse.shopping.common.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.utils.getSerializable
import woowacourse.shopping.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private val shoppingDBOpenHelper: ShoppingDBOpenHelper by lazy {
        ShoppingDBOpenHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initToolbar()
        setUpProductDetailCartButton()
        initPresenter()
    }

    override fun updateProductDetail(productModel: ProductModel) {
        binding.product = productModel
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

    private fun initBinding() {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.productDetailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initPresenter() {
        val product = intent.getSerializable<ProductModel>(EXTRA_KEY_PRODUCT) ?: return finish()
        presenter = ProductDetailPresenter(
            this,
            product = product.toDomain(),
            cartDao = CartDao(shoppingDBOpenHelper.writableDatabase)
        )
    }

    private fun startCartActivity() {
        val intent = CartActivity.createIntent(this)
        startActivity(intent)
    }

    private fun setUpProductDetailCartButton() {
        binding.productDetailCartButton.setOnClickListener {
            presenter.addToCart()
        }
    }

    companion object {
        private const val EXTRA_KEY_PRODUCT = "product"
        fun createIntent(context: Context, productModel: ProductModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(EXTRA_KEY_PRODUCT, productModel)
            return intent
        }
    }
}
