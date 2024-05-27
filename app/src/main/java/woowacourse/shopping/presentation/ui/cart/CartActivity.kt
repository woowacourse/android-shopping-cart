package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.cart.adapter.CartAdapter
import woowacourse.shopping.presentation.ui.detail.DetailActivity

class CartActivity : BaseActivity<ActivityCartBinding>(R.layout.activity_cart) {
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(
            (application as ShoppingApplication).cartRepository,
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
        binding.toolbarCart.setOnClickListener {
            setResult(RESULT_OK)
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
                is UIState.Error ->
                    showError(
                        state.exception.message ?: getString(R.string.unknown_error),
                    )
            }
        }
        viewModel.navigateToProductDetail.observe(this) { productId ->
            onProductClick(productId)
        }

        viewModel.navigateToShopping.observe(this) { modifiedProductIds ->
            if (modifiedProductIds.isNotEmpty()) {
                val resultIntent =
                    Intent().apply {
                        putExtra(EXTRA_KEY_MODIFIED_PRODUCT_IDS, modifiedProductIds.toLongArray())
                    }
                setResult(RESULT_OK, resultIntent)
            }
            finish()
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
        showMessage(errorMessage)
    }

    private fun onProductClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }

    companion object {
        const val EXTRA_KEY_MODIFIED_PRODUCT_IDS = "modifiedProductIds"

        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java).apply {
                putExtra(EXTRA_KEY_MODIFIED_PRODUCT_IDS, emptyList<Long>().toLongArray())
            }
        }
    }
}
