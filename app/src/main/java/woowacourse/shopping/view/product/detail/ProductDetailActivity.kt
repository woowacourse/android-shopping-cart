package woowacourse.shopping.view.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.cart.ShoppingCartActivity
import woowacourse.shopping.view.util.getSerializableExtraCompat

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        val product = intent.getSerializableExtraCompat<Product>(KEY_PRODUCT) ?: return
        viewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModelFactory(product, applicationContext),
            )[ProductDetailViewModel::class.java]
        initBindings(product)
        initObservers()
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
        binding.product = product
        binding.handler = viewModel
    }

    private fun initObservers() {
        viewModel.navigateEvent.observe(this) {
            val intent = ShoppingCartActivity.newIntent(this)
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
