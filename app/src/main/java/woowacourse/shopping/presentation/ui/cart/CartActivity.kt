package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.cart.adapter.CartAdapter
import woowacourse.shopping.presentation.ui.detail.DetailActivity

class CartActivity : BaseActivity<ActivityCartBinding>(R.layout.activity_cart) {
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(
            repository = CartRepositoryImpl((application as ShoppingApplication).cartDatabase),
        )
    }
    private lateinit var adapter: CartAdapter

    override fun onCreateSetup() {
        initializeViews()
        observeViewModel()
    }

    private fun initializeViews() {
        setUpToolbar()
        setUpRecyclerView()
        binding.viewModel = viewModel
    }

    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarCart
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView() {
        adapter = CartAdapter(viewModel)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.cartItemsState.observe(this) { state ->
            when (state) {
                is UIState.Success -> showData(state.data)
                is UIState.Empty -> showEmptyView()
                is UIState.Error -> showError(state.exception.message ?: getString(R.string.unknown_error))
            }
        }
        viewModel.navigateToProductDetail.observe(this) { productId ->
            onProductClick(productId)
        }
    }

    private fun showData(data: List<CartItem>) {
        adapter.loadData(data)
    }

    private fun showEmptyView() {
        binding.recyclerView.visibility = View.GONE
        binding.tvEmptyCart.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        showErrorMessage(errorMessage)
    }

    private fun onProductClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
