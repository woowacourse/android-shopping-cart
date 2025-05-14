package woowacourse.shopping.presentation.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(
            onClick = { product -> navigateToProductDetail(product) },
            onClickLoadMore = { viewModel.loadMore() },
        )
    }
    private val viewModel: ProductViewModel by viewModels { ProductViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_product)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.ibCart.setOnClickListener { navigateToCart() }

        initAdapter()
        observeViewModel()
        viewModel.fetchData()
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val itemCount = productAdapter.itemCount
                    val showLoadMore = viewModel.showLoadMore.value ?: false
                    return if (position == itemCount - 1 && showLoadMore) 2 else 1
                }
            }
        binding.rvProducts.layoutManager = layoutManager
        binding.rvProducts.adapter = productAdapter
    }

    private fun observeViewModel() {
        viewModel.products.observe(this) { products ->
            val showLoadMore = viewModel.showLoadMore.value ?: false
            productAdapter.setData(products, showLoadMore)
        }

        viewModel.showLoadMore.observe(this) { showLoadMore ->
            val products = viewModel.products.value.orEmpty()
            productAdapter.setData(products, showLoadMore)
        }
    }

    private fun navigateToProductDetail(product: Product) {
        val intent = ProductDetailActivity.newIntent(this, product)
        startActivity(intent)
    }

    private fun navigateToCart() {
        val intent = CartActivity.newIntent(this)
        startActivity(intent)
    }
}
