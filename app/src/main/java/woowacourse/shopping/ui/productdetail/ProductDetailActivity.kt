package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.fashionlist.FashionProductListActivity
import woowacourse.shopping.utils.intentSerializable
import kotlin.concurrent.thread

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    val cartRepository = CartRepository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        applyWindowInsets()
        val product = intent?.intentSerializable(EXTRA_PRODUCT, Product::class.java) ?: throw IllegalArgumentException("알 수 없는 값입니다.")
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.product = product
        binding.detailClickListener =
            object : DetailClickListener {
                override fun onAddToCartClick(product: Product) {
                    thread {
                        cartRepository.insert(product)
                    }
                    Toast.makeText(this@ProductDetailActivity, R.string.message_add_cart, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_close -> {
                startActivity(FashionProductListActivity.newIntent(this))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val EXTRA_PRODUCT = "product"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(
                    EXTRA_PRODUCT,
                    product,
                )
            }
        }
    }
}
