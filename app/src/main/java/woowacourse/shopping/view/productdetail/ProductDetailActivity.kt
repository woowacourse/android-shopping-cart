package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.CartProductDBHelper
import woowacourse.shopping.data.db.RecentProductDBHelper
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

        presenter = ProductDetailPresenter(this, product)
    }

    override fun setProductDetailView() {
        binding.product = presenter.product
    }

    private fun saveRecentProduct() {
        val db = RecentProductDBHelper(this).writableDatabase
        presenter.saveRecentProduct(db)
    }

    private fun setAddToCartClick() {
        val db = CartProductDBHelper(this).writableDatabase
        binding.btnAddToCart.setOnClickListener {
            presenter.saveCartProduct(db)
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
