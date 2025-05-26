package woowacourse.shopping.view.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.util.getSerializableExtraCompat

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        val product = intent.getSerializableExtraCompat<Product>(KEY_PRODUCT) ?: return
        val app = application as ShoppingApplication
        viewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModelFactory(
                    product,
                    app.cartProductRepository,
                    app.recentProductRepository,
                ),
            )[ProductDetailViewModel::class.java]
        initBindings(product)
        initObservers()

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpView() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBindings(product: Product) {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.handler = viewModel
        binding.itemQuantityControl.tvIncrease.setOnClickListener {
            viewModel.onQuantityIncreaseClick(product)
        }
        binding.itemQuantityControl.tvDecrease.setOnClickListener {
            viewModel.onQuantityDecreaseClick(product)
        }
    }

    private fun initObservers() {
        viewModel.addToCartEvent.observe(this) {
            Toast.makeText(this, R.string.message_add_to_cart, Toast.LENGTH_SHORT).show()
        }

        viewModel.lastProductClickEvent.observe(this) {
            val intent = newIntent(this, viewModel.lastViewedProduct.value?.product ?: return@observe)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }

        private const val KEY_PRODUCT = "product"
    }
}
