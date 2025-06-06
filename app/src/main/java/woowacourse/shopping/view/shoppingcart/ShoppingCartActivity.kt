package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.inventory.InventoryActivity
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
        goToMainActivity()
        return super.onOptionsItemSelected(item)
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.shopping_cart_toolbar_title)
        }
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goToMainActivity()
                }
            },
        )
    }

    private fun goToMainActivity() {
        val intent =
            InventoryActivity.newIntent(this@ShoppingCartActivity)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        binding.apply {
            rvShoppingCartList.adapter = ShoppingCartAdapter(this@ShoppingCartActivity)
            rvShoppingCartList.itemAnimator = null
            viewModel = this@ShoppingCartActivity.viewModel
            handler = this@ShoppingCartActivity
        }

        with(viewModel) {
            products.observe(this@ShoppingCartActivity) { page ->
                val adapter = binding.rvShoppingCartList.adapter as ShoppingCartAdapter
                adapter.submitList(page.items)
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
        product: CartProduct,
    ) {
        viewModel.increaseQuantity(position, product)
    }

    override fun onDecreaseQuantity(
        position: Int,
        product: CartProduct,
    ) {
        viewModel.decreaseQuantity(position, product)
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
