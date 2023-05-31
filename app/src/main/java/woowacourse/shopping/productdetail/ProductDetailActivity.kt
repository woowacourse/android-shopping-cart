package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shopping.domain.CartRepository
import com.shopping.domain.RecentRepository
import woowacourse.shopping.BundleKeys
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.datas.CartDBHelper
import woowacourse.shopping.datas.CartRepositoryImpl
import woowacourse.shopping.datas.RecentProductDBHelper
import woowacourse.shopping.datas.RecentRepositoryImpl
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.productcatalogue.ProductCatalogueActivity
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var binding: ActivityProductDetailBinding

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setResult(
                RESULT_OK,
                Intent(this@ProductDetailActivity, ProductCatalogueActivity::class.java)
            )
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        setSupportActionBar(binding.tbProductDetail)

        val productData =
            intent.getSerializableCompat(BundleKeys.KEY_PRODUCT) ?: ProductUIModel.dummy
        val recentRepository: RecentRepository =
            RecentRepositoryImpl(RecentProductDBHelper(this).writableDatabase)
        val cartRepository: CartRepository = CartRepositoryImpl(CartDBHelper(this).writableDatabase)

        presenter = ProductDetailPresenter(this, productData, recentRepository, cartRepository)
        presenter.attachCartProductData()
        binding.showAddCartDialog = ::navigateToAddToCartDialog
        presenter.getMostRecentProduct()
        presenter.insertRecentRepository(System.currentTimeMillis())

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun setCartProductData(cartProduct: CartProductUIModel) {
        binding.cartProduct = cartProduct
    }

    override fun showCartPage() {
        val intent = CartActivity.getIntent(binding.root.context)
        startActivity(intent)
        finish()
    }

    override fun setLatestProductVisibility() {
        binding.clRecentProductBox.visibility = View.VISIBLE
    }

    override fun showRecentProduct(recentProduct: RecentProductUIModel) {
        binding.recentProduct = recentProduct
    }

    override fun navigateToAddToCartDialog(cartProduct: CartProductUIModel) {
        AddCartDialog(this, cartProduct) { count ->
            presenter.addToCart(count)
        }.apply {
            val density = resources.displayMetrics.density * 1.2
            window?.setLayout(
                (DEFAULT_DIALOG_WIDTH * density).toInt(),
                (DEFAULT_DIALOG_HEIGHT * density).toInt()
            )
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cancel -> {
                setResult(RESULT_OK, Intent(this, ProductCatalogueActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val DEFAULT_DIALOG_WIDTH = 314
        private const val DEFAULT_DIALOG_HEIGHT = 150

        fun getIntent(context: Context): Intent {
            return Intent(context, ProductDetailActivity::class.java)
        }
    }
}
