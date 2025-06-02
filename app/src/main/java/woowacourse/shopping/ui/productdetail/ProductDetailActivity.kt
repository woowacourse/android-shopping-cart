package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.fashionlist.FashionProductListActivity
import woowacourse.shopping.utils.ViewModelFactory
import woowacourse.shopping.utils.intentSerializable

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        val product = intent?.intentSerializable(EXTRA_PRODUCT_ID, Product::class.java) ?: throw IllegalArgumentException("알 수 없는 값입니다.")
        val app = application as ShoppingCartApplication
        val factory = ViewModelFactory.createDetailViewModelFactory(
            app.productRepository,
            app.cartRepository,
            app.historyRepository,
            product
        )
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        applyWindowInsets()
        initObserver()
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.viewModel = viewModel
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "product"

        fun newIntent(
            context: Context,
            productId: Product,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(
                    EXTRA_PRODUCT_ID,
                    productId,
                )
            }
        }
    }
}
