package woowacourse.shopping.view.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.utils.getSerializableExtraCompat
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductDetailViewModel
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private val dialog by lazy {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(R.string.server_error_message)
            .setPositiveButton(R.string.confirm, null)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val product = intent.getSerializableExtraCompat<Product>(KEY_PRODUCT) ?: return
        initViewModel(product)
        initObservers()
    }

    private fun initViewModel(product: Product) {
        viewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModel.provideFactory(product),
            )[ProductDetailViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObservers() {
        viewModel.navigateEvent.observe(this) {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }
        viewModel.errorEvent.observe(this) {
            dialog.show()
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
