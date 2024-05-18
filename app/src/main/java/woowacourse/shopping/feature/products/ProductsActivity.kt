package woowacourse.shopping.feature.products

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.detail.ProductDetailActivity
import woowacourse.shopping.feature.products.adapter.ProductsAdapter
import woowacourse.shopping.feature.products.viewmodel.ProductsViewModel
import woowacourse.shopping.feature.products.viewmodel.ProductsViewModelFactory

class ProductsActivity : AppCompatActivity() {
    private val binding: ActivityProductsBinding by lazy { ActivityProductsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ProductsViewModel> { ProductsViewModelFactory(ProductDummyRepository) }
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initializeView()
    }

    private fun initializeView() {
        initializeProductAdapter()
        initializeToolbar()
        initializePage()
        viewModel.loadPage()
    }

    private fun initializeProductAdapter() {
        adapter =
            ProductsAdapter(onClickProductItem = { productId ->
                navigateToProductDetailView(productId)
            })
        binding.rvProducts.adapter = adapter
        viewModel.products.observe(this) {
            adapter.insertProducts(it)
        }
    }

    private fun navigateToProductDetailView(productId: Long) {
        ProductDetailActivity.newIntent(this, productId)
            .also { startActivity(it) }
    }

    private fun initializeToolbar() {
        binding.toolbarProducts.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_cart -> {
                    navigateToCartView()
                }
            }
            false
        }
    }

    private fun navigateToCartView() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun initializePage() {
        val onScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPosition = (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    viewModel.changeSeeMoreVisibility(lastPosition)
                }
            }
        binding.rvProducts.addOnScrollListener(onScrollListener)
    }
}
