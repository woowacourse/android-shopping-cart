package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.base.BaseActivity

class CartActivity : BaseActivity<ActivityCartBinding>(R.layout.activity_cart) {
    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter = CartAdapter(createAdapterOnClickHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvCart.adapter = cartAdapter

        viewModel.products.observe(this) { products ->
            cartAdapter.replaceItems(products)
        }

        viewModel.updateCartProducts(5)
    }

    private fun createAdapterOnClickHandler() =
        object : CartViewHolder.OnClickHandler {
            override fun onRemoveCartProductClick(id: Int) {
                viewModel.removeCartProduct(id)
            }
        }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
