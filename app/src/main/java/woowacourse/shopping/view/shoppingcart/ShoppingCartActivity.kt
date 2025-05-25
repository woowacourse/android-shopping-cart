package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.view.base.BaseActivity

class ShoppingCartActivity :
    BaseActivity<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart),
    ShoppingCartEventHandler {
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.shopping_cart_toolbar_title)
        }

        val shoppingApplication = application as ShoppingApplication
        val factory =
            ShoppingCartViewModel.createFactory(
                shoppingApplication.inventoryRepository,
                shoppingApplication.shoppingCartRepository,
            )
        viewModel = ViewModelProvider(this, factory)[ShoppingCartViewModel::class.java]
        initRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        binding.apply {
            rvShoppingCartList.adapter = ShoppingCartAdapter(this@ShoppingCartActivity)
            rvShoppingCartList.itemAnimator = null
            viewModel = this@ShoppingCartActivity.viewModel
            handler = this@ShoppingCartActivity
        }

        with(viewModel) {
            cartItems.observe(this@ShoppingCartActivity) { page ->
                val adapter = binding.rvShoppingCartList.adapter as ShoppingCartAdapter
                adapter.updateCartItems(page.items)
            }
            requestPage(0)
        }
    }

    override fun onGoToPreviousPage() {
        viewModel.requestPreviousPage()
    }

    override fun onGoToNextPage() {
        viewModel.requestNextPage()
    }

    override fun onIncreaseQuantity(
        position: Int,
        cartProduct: CartProduct,
    ) {
        viewModel.increaseQuantity(position, cartProduct)
    }

    override fun onDecreaseQuantity(
        position: Int,
        cartProduct: CartProduct,
    ) {
        viewModel.decreaseQuantity(position, cartProduct)
    }

    override fun onRemoveCartItem(cartProduct: CartProduct) {
        viewModel.removeCartItem(cartProduct)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
