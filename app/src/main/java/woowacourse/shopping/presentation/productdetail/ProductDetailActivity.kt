package woowacourse.shopping.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbAdapter
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.MockProductRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding

    private val productId: Int by lazy {
        intent.getIntExtra(PRODUCT_ID_KEY_VALUE, 0)
    }

    private val presenter: ProductDetailContract.Presenter by lazy {
        val cartRepository: CartRepository = CartDbAdapter(CartDbHelper(this))
        ProductDetailPresenter(this, cartRepository, MockProductRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun showCompleteMessage(productName: String) {
        Toast.makeText(
            this,
            getString(R.string.put_in_cart_complete_message, productName),
            Toast.LENGTH_LONG,
        ).show()
    }

    override fun showProductDetail(productModel: ProductModel) {
        binding.productModel = productModel
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_close -> {
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail_toolbar, menu)
        return true
    }

    private fun initView() {
        setToolbar()
        presenter.loadProductDetail(productId)
        binding.buttonPutInCart.setOnClickListener { putInCart() }
    }

    private fun putInCart() {
        presenter.putProductInCart()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarProductDetail.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    companion object {
        private const val PRODUCT_ID_KEY_VALUE = "PRODUCT_ID_KEY_VALUE"

        fun getIntent(context: Context, productId: Int): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID_KEY_VALUE, productId)
            }
        }
    }
}
