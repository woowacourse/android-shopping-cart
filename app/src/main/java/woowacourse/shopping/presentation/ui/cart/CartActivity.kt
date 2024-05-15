package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.UiState

class CartActivity : BaseActivity<ActivityCartBinding>(), CartHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_cart

    private val cartAdapter: CartAdapter = CartAdapter(this)

    private val cartViewModel: CartViewModel by viewModels()

    override fun initStartView() {
        binding.rvCarts.adapter = cartAdapter

        cartViewModel.carts.observe(this) {
            when (it) {
                is UiState.Finish -> {
                    cartAdapter.updateList(it.data)
                }
                is UiState.None -> {
                }
                is UiState.Error -> {
                }
            }
        }
    }

    override fun onDeleteClick(product: Product) {
        cartViewModel.deleteProduct(product)
    }

    companion object {
        fun start(context: Context) {
            Intent(context, CartActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
