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
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.QuantityClickListener
import woowacourse.shopping.ui.fashionlist.FashionProductListActivity
import woowacourse.shopping.utils.intentSerializable

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        val product = intent?.intentSerializable(EXTRA_PRODUCT, Product::class.java) ?: throw IllegalArgumentException("알 수 없는 값입니다.")
        viewModel = ViewModelProvider(this, DetailViewModelFactory(product))[DetailViewModel::class.java]
        applyWindowInsets()
        initObserver()
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.product = product
        binding.detailClickListener =
            object : DetailClickListener {
                override fun onAddToCartClick(cartItem: CartItem) {
                    viewModel.insertCartItem(cartItem)
                    Toast.makeText(this@ProductDetailActivity, R.string.message_add_cart, Toast.LENGTH_SHORT).show()
                }

                override fun onRecentProductClick() {
                    val intent =
                        newIntent(this@ProductDetailActivity, viewModel.lastItem.value ?: product)
                    startActivity(intent)
                    finish()
                }
            }
        binding.quantityClickListener =
            object : QuantityClickListener {
                override fun onIncreaseClick(cartItem: CartItem) {
                    viewModel.increaseQuantity()
                }

                override fun onDecreaseClick(cartItem: CartItem) {
                    viewModel.decreaseQuantity()
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

    private fun initObserver() {
        viewModel.cartItem.observe(this) {
            binding.cartItem = it
        }
        viewModel.lastItem.observe(this) {
            binding.recentProduct = it
        }
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
