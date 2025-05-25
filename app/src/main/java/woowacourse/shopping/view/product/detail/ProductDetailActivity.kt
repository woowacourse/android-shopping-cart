package woowacourse.shopping.view.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityDetailProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.utils.getSerializableExtraCompat
import woowacourse.shopping.view.component.ProductCountViewModel

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val product: Product by lazy {
        intent.getSerializableExtraCompat<Product>(KEY_PRODUCT_DETAIL)
    }
    private val productDetailViewModel: ProductDetailViewModel by viewModels { ProductDetailViewModel.Factory }
    private val productCountViewModel: ProductCountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initDataBinding()
        fetchData()
        observeData()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initDataBinding() {
        binding.apply {
            lifecycleOwner = this@ProductDetailActivity
            productDetailVM = productDetailViewModel
            productCountVM = productCountViewModel
        }
    }

    private fun fetchData() {
        productDetailViewModel.fetchData(product)
    }

    private fun observeData() {
        productCountViewModel.isMinimumProductCount.observe(this) { isMinimumProductCount ->
            if (isMinimumProductCount) {
                Toast
                    .makeText(
                        this,
                        getString(R.string.minimum_product_count),
                        Toast.LENGTH_SHORT,
                    ).show()
            }
        }
        productDetailViewModel.cartAddedEvent.observe(this) {
            Toast
                .makeText(this, R.string.detail_product_add_cart_success, Toast.LENGTH_SHORT)
                .show()
        }
        productDetailViewModel.exitEvent.observe(this) { finish() }
        productDetailViewModel.recentlyViewedProductEvent.observe(this) {
            val intent =
                productDetailViewModel.recentlyViewedProduct.value?.let { it ->
                    newIntent(
                        this,
                        it,
                    )
                }
            startActivity(intent)
        }
    }

    companion object {
        private const val KEY_PRODUCT_DETAIL = "product_data"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java)
                .apply { putExtra(KEY_PRODUCT_DETAIL, product) }
    }
}
