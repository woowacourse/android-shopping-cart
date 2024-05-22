package woowacourse.shopping.view.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.detail.DetailActivity
import woowacourse.shopping.view.shopping.adapter.ShoppingAdapter
import woowacourse.shopping.view.shopping.adapter.ShoppingType.Companion.LOAD_MORE_BUTTON_VIEW_TYPE
import woowacourse.shopping.view.state.UIState

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var adapter: ShoppingAdapter
    private val viewModel: ShoppingViewModel by viewModels {
        ShoppingViewModelFactory(ShoppingRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAdapter()
        setUpDataBinding()
        observeViewModel()
    }

    private fun setUpDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setUpAdapter() {
        adapter = ShoppingAdapter(viewModel)
        binding.rvProductList.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (adapter.getItemViewType(position) == LOAD_MORE_BUTTON_VIEW_TYPE) return 2
                    return 1
                }
            }
        binding.rvProductList.layoutManager = layoutManager
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

    private fun showData(data: List<Product>) {
        adapter.loadData(data, viewModel.canLoadMore.value ?: false)
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
