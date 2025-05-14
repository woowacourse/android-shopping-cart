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
import woowacourse.shopping.utils.getSerializableExtraCompat

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductDetailViewModel
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val product = intent.getSerializableExtraCompat<Product>(KEY_PRODUCT)

        if (product == null) {
            return
        } else {
            viewModel =
                ViewModelProvider(
                    this,
                    ProductDetailViewModel.provideFactory(product),
                )[ProductDetailViewModel::class.java]
        }
        binding.vm = viewModel
        binding.lifecycleOwner = this
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
