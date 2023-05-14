package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBRepository
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.util.getSerializableCompat

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding

    // todo null인 경우 처리하기
    private val product: ProductUiModel by lazy { intent.getSerializableCompat(PRODUCT_KEY)!! }
    private val presenter: ProductDetailPresenter by lazy {
        ProductDetailPresenter(
            view = this,
            product = product,
            repository = ShoppingDBRepository(
                shoppingDao = ShoppingDao(this),
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        binding.presenter = presenter
        setUpProductDetailToolbar()
        setUpProductDetailView()
    }

    private fun setUpProductDetailToolbar() {
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpProductDetailView() {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.imageProductDetail)

        binding.textProductName.text = product.name
        binding.textProductPrice.text = product.price.toString()
    }

    override fun navigateToShoppingCartView() {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val PRODUCT_KEY = "product"

        fun getIntent(context: Context, product: ProductUiModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
            }
            return intent
        }
    }
}
