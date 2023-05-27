package woowacourse.shopping.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbDao
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductMockServer
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.putincartdialog.PutInCartDialogFragment

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding

    private val presenter: ProductDetailContract.Presenter by lazy {
        val productRepository = ProductRepositoryImpl(ProductMockServer().url)
        val cartRepository: CartRepository =
            CartRepositoryImpl(CartDbDao(CartDbHelper(this)), productRepository)
        ProductDetailPresenter(this, cartRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun showRecentProduct(productModel: ProductModel) {
        binding.recentProductModel = productModel
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
        loadProductAndRecentProduct()
        binding.buttonPutInCart.setOnClickListener { showPunInCartDialog() }
    }

    private fun loadProductAndRecentProduct() {
        loadProduct()
        loadRecentProduct()
    }

    private fun loadProduct() {
        val productId = intent.getLongExtra(PRODUCT_ID_KEY, DEFAULT_VALUE)
        presenter.loadProductDetail(productId)
    }

    private fun loadRecentProduct() {
        val recentProductId = intent.getLongExtra(RECENT_PRODUCT_ID_KEY, DEFAULT_VALUE)
        if (recentProductId == DEFAULT_VALUE) {
            binding.cardRecentProductDetail.isVisible = false
            return
        }
        presenter.loadRecentProduct(recentProductId)
        binding.recentProductClickListener = ::showRecentProductDetail
    }

    private fun showRecentProductDetail(recentProductId: Long) {
        val intent = getIntent(context = this, productId = recentProductId, recentProductId = null)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun showPunInCartDialog() {
        val dialogFragment = PutInCartDialogFragment.newInstance(
            binding.productModel!!.name,
            binding.productModel!!.price,
            ::putInCart,
        )
        dialogFragment.show(supportFragmentManager, PutInCartDialogFragment.TAG)
    }

    private fun putInCart(count: Int) {
        presenter.putProductInCart(count)
        showToast()
        finish()
    }

    private fun showToast() {
        Toast.makeText(
            this,
            getString(R.string.put_in_cart_complete_message),
            Toast.LENGTH_SHORT,
        ).show()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarProductDetail.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    companion object {

        private const val PRODUCT_ID_KEY = "PRODUCT_ID_KEY"
        private const val RECENT_PRODUCT_ID_KEY = "RECENT_PRODUCT_ID_KEY"
        private const val DEFAULT_VALUE = -1L

        fun getIntent(context: Context, productId: Long, recentProductId: Long?): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID_KEY, productId)
                if (recentProductId != null) {
                    putExtra(RECENT_PRODUCT_ID_KEY, recentProductId)
                }
            }
        }
    }
}
