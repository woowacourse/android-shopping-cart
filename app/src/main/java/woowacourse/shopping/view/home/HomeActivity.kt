package woowacourse.shopping.view.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication.Companion.database
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.detail.DetailActivity
import woowacourse.shopping.view.home.adapter.product.ProductAdapter
import woowacourse.shopping.view.home.adapter.product.ShoppingItem.Companion.LOAD_MORE_BUTTON_VIEW_TYPE
import woowacourse.shopping.view.home.adapter.product.ShoppingItem.ProductItem
import woowacourse.shopping.view.home.adapter.recent.RecentAdapter
import woowacourse.shopping.view.state.UIState

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recentAdapter: RecentAdapter
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(ShoppingRepositoryImpl(), CartRepositoryImpl(database))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAdapter()
        setUpDataBinding()
        observeViewModel()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.updateProducts()
    }

    private fun setUpDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setUpAdapter() {
        productAdapter = ProductAdapter(viewModel, viewModel)
        binding.rvProductList.adapter = productAdapter
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (productAdapter.getItemViewType(position) == LOAD_MORE_BUTTON_VIEW_TYPE) return 2
                    return 1
                }
            }
        binding.rvProductList.layoutManager = layoutManager
        recentAdapter = RecentAdapter(viewModel)
        binding.rvRecentViewedProducts.adapter = recentAdapter
        binding.rvProductList.itemAnimator = null
        binding.rvRecentViewedProducts.itemAnimator = null
    }

    private fun observeViewModel() {
        viewModel.shoppingUiState.observe(this) { state ->
            when (state) {
                is UIState.Success -> showData(state.data)
                is UIState.Empty -> showData(emptyList())
                is UIState.Error ->
                    showError(
                        state.exception.message ?: getString(R.string.unknown_error),
                    )
            }
        }

        viewModel.updatedProductItem.observe(this) {
            productAdapter.updateProductQuantity(it)
        }

        viewModel.loadedProductItems.observe(this) {
            productAdapter.updateData(it)
        }

        viewModel.navigateToDetail.observe(this) {
            it.getContentIfNotHandled()?.let { productId ->
                navigateToDetail(productId)
            }
        }

        viewModel.navigateToCart.observe(this) {
            it.getContentIfNotHandled()?.let {
                navigateToCart()
            }
        }
    }

    private fun showData(data: List<ProductItem>) {
        productAdapter.loadData(data, viewModel.canLoadMore.value ?: false)
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun navigateToDetail(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }

    private fun navigateToCart() {
        startActivity(CartActivity.createIntent(this))
    }
}
