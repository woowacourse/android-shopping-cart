package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import java.text.DecimalFormat

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private val presenter: ProductDetailPresenter by lazy {
        ProductDetailPresenter(
            this,
            ProductRepositoryImpl,
            CartRepositoryImpl(this, ProductRepositoryImpl),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        presenter.loadProduct(intent.getLongExtra(PRODUCT_ID, -1))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_close -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setProduct(product: ProductDetailUIState) {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.ivProductDetail)

        binding.tvProductDetailName.text = product.name
        binding.tvProductDetailPrice.text =
            getString(R.string.product_price).format(DECIMAL_FORMAT.format(product.price))
        binding.btnProductDetailAdd.setOnClickListener {
            presenter.addProductToCart(product.id)
            moveToCartActivity()
        }
    }

    override fun showErrorMessage() {
        Toast.makeText(this, "존재하지 않는 상품입니다", Toast.LENGTH_SHORT).show()
    }

    private fun moveToCartActivity() {
        finish()
        CartActivity.startActivity(this)
    }

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"
        private val DECIMAL_FORMAT = DecimalFormat("#,###")

        fun startActivity(context: Context, productId: Long) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
            context.startActivity(intent)
        }
    }
}
