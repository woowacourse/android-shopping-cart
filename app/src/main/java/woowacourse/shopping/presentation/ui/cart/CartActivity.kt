package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory

class CartActivity : BindingActivity<ActivityCartBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_cart

    private val viewModel: CartViewModel by viewModels { ViewModelFactory() }
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }

    override fun initStartView() {
        initTitle()

        binding.cartActionHandler = viewModel
        binding.viewModel = viewModel

        viewModel.loadProductByOffset()
        binding.rvCarts.adapter = cartAdapter

        initObserver()
    }

    private fun initObserver() {
        viewModel.carts.observe(this) {
            when (it) {
                is UiState.None -> {}
                is UiState.Success -> {
                    cartAdapter.updateList(it.data)
                }
            }
        }
        viewModel.errorHandler.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initTitle() {
        title = getString(R.string.cart_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val OFFSET_BASE = 1

        fun start(context: Context) {
            Intent(context, CartActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
