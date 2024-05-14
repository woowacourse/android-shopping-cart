package woowacourse.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private val productViewModel by viewModels<ProductViewModel>()
    private val productRepository: ProductRepository = ProductRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        productViewModel.load(productRepository, productId())
        productViewModel.product.observe(this) {
            binding.product = it
        }
    }

    private fun productId(): Long = intent.getLongExtra(PRODUCT_ID_KEY, PRODUCT_ID_DEFAULT_VALUE)

    companion object {
        private const val PRODUCT_ID_KEY = "product_id_key"
        private const val PRODUCT_ID_DEFAULT_VALUE = -1L

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent  {
            return Intent(context, ProductDetailActivity::class.java)
                .putExtra(PRODUCT_ID_KEY, productId)
        }
    }
}
