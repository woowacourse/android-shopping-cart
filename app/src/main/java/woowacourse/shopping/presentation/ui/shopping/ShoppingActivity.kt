package woowacourse.shopping.presentation.ui.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingProduct
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.DetailActivity

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var shoppingAdapter: ShoppingAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter

    private val viewModel: ShoppingViewModel by viewModels {
        ShoppingViewModelFactory(
            shoppingItemsRepository = ShoppingItemsRepositoryImpl(),
            cartItemsRepository = CartRepositoryImpl(ShoppingApplication.getInstance().cartDatabase),
            recentProductRepository = RecentProductRepositoryImpl(ShoppingApplication.getInstance().recentProductDatabase),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        observeViewModel()
    }

    private fun setUpRecyclerView() {
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        setUpRecyclerViewAdapter()
        checkLoadMoreBtnVisibility()
    }

    private fun setUpRecyclerViewAdapter() {
        shoppingAdapter = ShoppingAdapter(viewModel, viewModel)
        recentProductAdapter = RecentProductAdapter(viewModel)

        binding.rvProductList.adapter = shoppingAdapter
        binding.horizontalView.rvRecentProduct.adapter = recentProductAdapter

        try {
            viewModel.products.observe(this) { products ->
                shoppingAdapter.loadData(products)
            }
        } catch (exception: Exception) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
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

        viewModel.shoppingProducts.observe(this) {
            shoppingAdapter.loadShoppingProductData(it)
        }

        viewModel.recentProducts.observe(this) {
            recentProductAdapter.updateProducts(it)
        }
    }

    private fun checkLoadMoreBtnVisibility() {
        binding.rvProductList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.showLoadMoreByCondition()
                    } else {
                        if (dy < 0) viewModel.hideLoadMore()
                    }
                }
            },
        )
    }

    private fun showData(data: List<Product>) {
        shoppingAdapter.loadData(data)
        shoppingAdapter.loadShoppingProductData(
            data.map {
                ShoppingProduct(
                    product = it,
                    quantity = viewModel.fetchQuantity(it.id),
                )
            },
        )
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun navigateToCart() {
        startActivity(CartActivity.createIntent(context = this))
    }

    private fun navigateToDetail(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }
}
