package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.base.BaseActivity

class CartActivity : BaseActivity<ActivityCartBinding>(R.layout.activity_cart) {
    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter = CartAdapter(createAdapterOnClickHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Cart"

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvCart.adapter = cartAdapter

        viewModel.products.observe(this) { products ->
            cartAdapter.replaceItems(products)
        }

        viewModel.currentPage.observe(this) { page ->
            viewModel.updateCartProducts()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
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
