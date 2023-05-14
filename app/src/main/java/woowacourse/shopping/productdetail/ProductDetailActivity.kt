package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.common.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.common.data.database.dao.CartDao
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.utils.getSerializable
import woowacourse.shopping.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var productModel: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExtras()

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.product_detail_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setUpProductDetailCartButton()

        initPresenter()
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

    override fun updateProductDetail(productModel: ProductModel) {
        binding.product = productModel
    }

    override fun showCart() {
        startCartActivity()
    }

    private fun startCartActivity() {
        val intent = CartActivity.createIntent(this)
        startActivity(intent)
    }

    private fun initExtras() {
        productModel = intent.getSerializable(EXTRA_KEY_PRODUCT) ?: return finish()
    }

    private fun setUpProductDetailCartButton() {
        binding.productDetailCartButton.setOnClickListener {
            presenter.addToCart()
        }
    }

    private fun initPresenter() {
        val db = ShoppingDBOpenHelper(this).writableDatabase
        presenter = ProductDetailPresenter(
            this,
            productModel = productModel,
            cartDao = CartDao(db)
        )
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
