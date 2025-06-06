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
import woowacourse.shopping.view.ActivityResult
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.shoppingcart.adapter.ShoppingCartAdapter

class ShoppingCartActivity :
    BaseActivity<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart),
    ShoppingCartEventHandler {
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()

        val application = application as ShoppingApplication
        val factory =
            ShoppingCartViewModel.createFactory(
                application.inventoryRepository,
                application.shoppingCartRepository,
            )
        viewModel = ViewModelProvider(this, factory)[ShoppingCartViewModel::class.java]
        initRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.shopping_cart_toolbar_title)
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rvShoppingCartList.adapter = ShoppingCartAdapter(this@ShoppingCartActivity)
            rvShoppingCartList.itemAnimator = null
            viewModel = this@ShoppingCartActivity.viewModel
            handler = this@ShoppingCartActivity
        }

        with(viewModel) {
            val adapter = binding.rvShoppingCartList.adapter as ShoppingCartAdapter

            products.observe(this@ShoppingCartActivity) { page ->
                adapter.submitList(page.items)
            }

            cartUpdateEvent.observe(this@ShoppingCartActivity) { item ->
                adapter.updateCartProduct(item)
            }

            modifiedProductIds.observe(this@ShoppingCartActivity) { modifiedProductIds ->
                val intent =
                    Intent().putIntegerArrayListExtra(
                        ActivityResult.CART_ITEM_MODIFIED.key,
                        ArrayList(modifiedProductIds),
                    )
                setResult(ActivityResult.CART_ITEM_MODIFIED.hashCode(), intent)
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

    override fun onIncreaseQuantity(product: CartProduct) {
        viewModel.increaseQuantity(product)
    }

    override fun onDecreaseQuantity(product: CartProduct) {
        viewModel.decreaseQuantity(product)
    }

    override fun onRemoveProduct(product: CartProduct) {
        viewModel.removeCartItem(product)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
