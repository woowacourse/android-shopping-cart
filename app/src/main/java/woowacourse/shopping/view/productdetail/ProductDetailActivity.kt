package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.CartProductDao
import woowacourse.shopping.data.db.RecentProductDao
import woowacourse.shopping.data.repository.CartProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    override lateinit var presenter: ProductDetailContract.Presenter

    private var _binding: ActivityProductDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        setSupportActionBar(binding.tbProductDetail)
        setPresenter()
        setProductDetailView()
        saveRecentProduct()
        setAddToCartClick()
    }

    private fun setPresenter() {
        val product = intent.getSerializableCompat<ProductUIModel>(BundleKeys.KEY_PRODUCT)
            ?: throw IllegalStateException(NON_FOUND_KEY_ERROR)

        presenter =
            ProductDetailPresenter(
                view = this,
                product = product,
                cartProductRepository = CartProductRepositoryImpl(CartProductDao(this)),
                recentProductsRepository = RecentProductRepositoryImpl(RecentProductDao(this))
            )
    }

    override fun setProductDetailView() {
        binding.product = presenter.product
    }

    private fun saveRecentProduct() {
        presenter.saveRecentProduct()
    }

    private fun setAddToCartClick() {
        val db = CartProductDao(this).writableDatabase
        binding.btnAddToCart.setOnClickListener {
            presenter.saveCartProduct()
        }
    }

    override fun showCartPage() {
        startActivity(ShoppingCartActivity.intent(binding.root.context))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val NON_FOUND_KEY_ERROR = "일치하는 키가 없습니다."
        fun intent(context: Context): Intent {
            return Intent(context, ProductDetailActivity::class.java)
        }
    }
}
