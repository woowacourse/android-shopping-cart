package woowacourse.shopping.ui.detailedProduct

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityDetailedProductBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.repositoryImpl.MockWeb
import woowacourse.shopping.repositoryImpl.RemoteProductRepository
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.cart.cartDialog.CartDialog
import woowacourse.shopping.utils.ActivityUtils
import woowacourse.shopping.utils.SharedPreferenceUtils
import woowacourse.shopping.utils.getSerializableExtraCompat

class DetailedProductActivity : AppCompatActivity(), DetailedProductContract.View {
    private lateinit var binding: ActivityDetailedProductBinding
    private lateinit var presenter: DetailedProductContract.Presenter
    private lateinit var mockWeb: MockWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMockWeb()
        initBinding()
        initPresenter()
        initToolbar()
    }

    private fun initMockWeb() {
        val thread = Thread { mockWeb = MockWeb() }
        thread.start()
        thread.join()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_product)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initPresenter() {
        presenter = DetailedProductPresenter(
            this,
            intent.getSerializableExtraCompat(KEY_PRODUCT)
                ?: return ActivityUtils.keyError(this, KEY_PRODUCT),
            SharedPreferenceUtils(this),
            RemoteProductRepository(mockWeb.url),
            CartDatabase(this),
            RecentProductDatabase(this)
        )
        binding.presenter = presenter
        presenter.setUpLastProduct()
        presenter.setUpProductDetail()
        presenter.addProductToRecent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> finish()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun setProductDetail(product: ProductUIModel, lastProduct: ProductUIModel?) {
        binding.product = product
        binding.lastProduct = lastProduct
    }

    override fun navigateToCart() {
        startActivity(CartActivity.getIntent(this))
    }

    override fun navigateToDetailedProduct(product: ProductUIModel) {
        startActivity(
            getIntent(this, product).apply {
                flags = FLAG_ACTIVITY_CLEAR_TOP
            }
        )
    }

    override fun navigateToAddToCartDialog(product: ProductUIModel) {
        CartDialog(this, product.name, product.price) { count ->
            presenter.addProductToCart(count)
        }.apply {
            val density = resources.displayMetrics.density * 1.2
            window?.setLayout((314 * density).toInt(), (150 * density).toInt())
            show()
        }
    }

    companion object {
        private const val KEY_PRODUCT = "KEY_PRODUCT"
        fun getIntent(context: Context, product: ProductUIModel): Intent {
            return Intent(context, DetailedProductActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }
}
