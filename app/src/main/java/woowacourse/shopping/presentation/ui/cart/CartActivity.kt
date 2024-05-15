package woowacourse.shopping.presentation.ui.cart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.UiState

class CartActivity : BindingActivity<ActivityCartBinding>(), CartHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_cart

    private val cartAdapter: CartAdapter = CartAdapter(this)

    private val viewModel: CartViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun initStartView() {
        binding.rvCarts.adapter = cartAdapter

        binding.btnLeft.setOnClickListener {
            viewModel.minus()
        }

        binding.btnRight.setOnClickListener {
            viewModel.plus()
        }

        viewModel.carts.observe(this) {
            when (it) {
                is UiState.Finish -> {
                    cartAdapter.updateList(it.data)
                    binding.tvPageCount.text = (viewModel.offSet + 1).toString()

                    if (viewModel.maxOffset <= 0) {
                        binding.layoutPage.isVisible = false
                    } else {
                        binding.layoutPage.isVisible = true
                        if (viewModel.offSet == viewModel.maxOffset) {
                            binding.btnRight.isEnabled = false
                        } else {
                            binding.btnRight.isEnabled = true
                        }

                        if (viewModel.offSet == 0) {
                            binding.btnLeft.isEnabled = false
                        } else {
                            binding.btnLeft.isEnabled = true
                        }
                    }
                }

                is UiState.None -> {
                }

                is UiState.Error -> {
                }
            }
        }
    }

    override fun onDeleteClick(product: Product) {
        viewModel.deleteProduct(product)
    }

    companion object {
        fun start(context: Context) {
            Intent(context, CartActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
