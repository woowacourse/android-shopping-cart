package woowacourse.shopping.presentation.ui.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.DetailActivity

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var adapter: ShoppingAdapter
    private val viewModel: ShoppingViewModel by viewModels {
        ShoppingViewModelFactory(
            ShoppingItemsRepositoryImpl(),
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
        adapter = ShoppingAdapter(viewModel)
        binding.rvProductList.adapter = adapter

        try {
            viewModel.products.observe(
                this,
            ) { products ->
                adapter.loadData(products)
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
        adapter.loadData(data)
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
