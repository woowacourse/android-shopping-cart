package woowacourse.shopping.presentation.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(
            emptyList(),
            { product -> navigateToProductDetail(product) },
        )
    }
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_product)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initAdapter()
        observeViewModel()

        viewModel.fetchData()
    }

    private fun initAdapter() {
        binding.rvProducts.adapter = productAdapter
    }

    private fun observeViewModel() {
        viewModel.products.observe(this) {
            productAdapter.setData(it)
        }
    }

    private fun navigateToProductDetail(product: Product) {
        val intent = ProductDetailActivity.newIntent(this, product)
        startActivity(intent)
    }
}
