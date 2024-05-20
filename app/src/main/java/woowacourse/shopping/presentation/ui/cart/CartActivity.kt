package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory

class CartActivity : BindingActivity<ActivityCartBinding>(), CartHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_cart

    private val cartAdapter: CartAdapter = CartAdapter(this)

    private val viewModel: CartViewModel by viewModels { ViewModelFactory() }

    override fun initStartView(savedInstanceState: Bundle?) {
        initActionBarTitle()
        initButtonClickListener()
        initCartAdapter()
        viewModel.loadProductByPage()
        observeErrorEventUpdates()
        observeCartUpdates()
    }

    private fun initActionBarTitle() {
        title = getString(R.string.cart_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initButtonClickListener() {
        binding.btnLeft.setOnClickListener {
            viewModel.minus()
        }
        binding.btnRight.setOnClickListener {
            viewModel.plus()
        }
    }

    private fun initCartAdapter() {
        binding.rvCarts.adapter = cartAdapter
    }

    private fun observeErrorEventUpdates() {
        viewModel.error.observe(this) {
            when (it) {
                true -> showToast(getString(R.string.error_load_cart))
                false -> {}
            }
        }
    }

    private fun observeCartUpdates() {
        viewModel.carts.observe(this) {
            when (it) {
                is UiState.None -> {}
                is UiState.Success -> handleSuccessState(it)
            }
        }
    }

    private fun handleSuccessState(it: UiState.Success<List<Cart>>) {
        cartAdapter.updateList(it.data)
        with(binding) {
            layoutPage.isVisible = viewModel.maxPage > 0
            btnRight.isEnabled = viewModel.currentPage < viewModel.maxPage
            btnLeft.isEnabled = viewModel.currentPage > 0
            tvPageCount.text = (viewModel.currentPage + PAGE_OFFSET).toString()
        }
    }

    override fun onDeleteClick(product: Product) {
        viewModel.deleteProduct(product)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val PAGE_OFFSET = 1

        fun start(context: Context) {
            Intent(context, CartActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
