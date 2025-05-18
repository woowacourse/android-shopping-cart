package woowacourse.shopping.view.products

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var adapter: ProductsAdapter
    private val viewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        observeProductsView()
        setCartButtonClickListener()
        setupScrollListenerForMoreButton()
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupScrollListenerForMoreButton() {
        binding.rvProducts.addOnScrollListener(
            ProductsScrollListener(binding.rvProducts.layoutManager as GridLayoutManager) { canLoadMore ->
                val isAllFetched = viewModel.isAllProductsFetched.value ?: false
                viewModel.updateButtonVisibility(canLoadMore && !isAllFetched)
            },
        )
    }

    private fun initRecyclerView() {
        adapter = ProductsAdapter { product -> navigateToProductDetail(product) }
        binding.rvProducts.adapter = adapter
        binding.rvProducts.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING_DP))
    }

    private fun observeProductsView() {
        viewModel.productsInShop.observe(this) { list ->
            adapter.updateProductsView(list)
        }
    }

    private fun setCartButtonClickListener() {
        binding.cartImageBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun navigateToProductDetail(product: Product) {
        val intent =
            Intent(this, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_DATA_KEY, product)
            }
        startActivity(intent)
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SPACING_DP = 12f
        const val PRODUCT_DATA_KEY = "product"
    }
}
