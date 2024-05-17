package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.detail.DetailActivity

class CartActivity : AppCompatActivity(), CartClickListener {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(
            repository = CartRepositoryImpl((application as ShoppingApplication).database),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()

        adapter = CartAdapter(this)

        binding.lifecycleOwner = this
        binding.recyclerView.adapter = adapter
        binding.vmShoppingCart = viewModel

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UIState.Success -> showData(state.data)
                is UIState.Empty -> showData(emptyList())
                is UIState.Error ->
                    showError(
                        state.exception.message ?: getString(R.string.unknown_error),
                    )
            }
        }
    }

    private fun showData(data: List<CartItem>) {
        adapter.loadData(data)
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        viewModel.loadCartItems()
    }

    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarCart
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onItemClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }

    override fun onDeleteItemClick(itemId: Long) {
        viewModel.deleteItem(itemId)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
