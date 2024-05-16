package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory

class CartActivity : BindingActivity<ActivityCartBinding>(), CartHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_cart

    private val cartAdapter: CartAdapter = CartAdapter(this)

    private val viewModel: CartViewModel by viewModels { ViewModelFactory() }

    override fun initStartView() {
        initTitle()
        initClickListener()

        binding.rvCarts.adapter = cartAdapter
        viewModel.loadProductByOffset()

        viewModel.carts.observe(this) {
            when (it) {
                is UiState.None -> {
                }

                is UiState.Finish -> {
                    cartAdapter.updateList(it.data)
                    with(binding) {
                        layoutPage.isVisible = viewModel.maxOffset > 0
                        btnRight.isEnabled = viewModel.offSet < viewModel.maxOffset
                        btnLeft.isEnabled = viewModel.offSet > 0
                        tvPageCount.text = (viewModel.offSet + OFFSET_BASE).toString()
                    }
                }

                is UiState.Error -> {
                    Toast.makeText(this, getString(R.string.error_load_cart), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initClickListener() {
        binding.btnLeft.setOnClickListener {
            viewModel.minus()
        }
        binding.btnRight.setOnClickListener {
            viewModel.plus()
        }
    }

    private fun initTitle() {
        title = getString(R.string.cart_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDeleteClick(product: Product) {
        viewModel.deleteProduct(product)
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
