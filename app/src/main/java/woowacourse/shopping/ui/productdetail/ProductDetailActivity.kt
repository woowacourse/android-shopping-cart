package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.intentSerializable

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.createFactory(getIntentProduct())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        applyWindowInsets()
        setOnBackPressedCallback()

        initViews()
        initObserve()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            },
        )
    }

    private fun initViews() {
        initAppbar()
        setupBindings()
    }

    private fun initAppbar() {
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupBindings() {
        binding.viewModel = viewModel
    }

    private fun initObserve() {
        viewModel.eventAddedCartToast.observe(this) {
            showAddedCartToast()
        }
    }

    private fun showAddedCartToast() {
        Toast.makeText(
            this@ProductDetailActivity,
            R.string.message_add_cart,
            Toast.LENGTH_SHORT,
        ).show()
    }

    private fun getIntentProduct(): Product {
        return intent?.intentSerializable(EXTRA_PRODUCT, Product::class.java)
            ?: throw IllegalArgumentException(ERROR_INVALID_INTENT_VALUE)
    }

    companion object {
        private const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        private const val ERROR_INVALID_INTENT_VALUE = "알 수 없는 값입니다."

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
