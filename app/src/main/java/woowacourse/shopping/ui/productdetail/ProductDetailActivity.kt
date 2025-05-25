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
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.common.QuantityChangeListener

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.createFactory(getProductIdFromIntent())
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.quantityChangeListener = initQuantityChangerListener()
    }

    private fun initQuantityChangerListener() = object : QuantityChangeListener {
        override fun increase(productId: Long) {
            viewModel.increaseQuantity()
        }

        override fun decrease(productId: Long) {
            viewModel.decreaseQuantity()
        }
    }

    private fun initObserve() {
        viewModel.eventAddedCartToast.observe(this) {
            showAddedCartToast()
            startActivity(CartActivity.newIntent(this))
            finish()
        }
    }


    private fun showAddedCartToast() {
        Toast.makeText(
            this@ProductDetailActivity,
            R.string.message_add_cart,
            Toast.LENGTH_SHORT,
        ).show()
    }

    private fun getProductIdFromIntent(): Long {
        return intent.getLongExtra(EXTRA_PRODUCT, 0)
    }

    companion object {
        private const val EXTRA_PRODUCT = "EXTRA_PRODUCT"

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT, productId)
            }
        }
    }
}
