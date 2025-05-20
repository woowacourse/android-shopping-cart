package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartAdapter
import woowacourse.shopping.ui.cart.adapter.CartViewHolder
import woowacourse.shopping.ui.common.DataBindingActivity
import woowacourse.shopping.ui.model.ResultCode

class CartActivity : DataBindingActivity<ActivityCartBinding>(R.layout.activity_cart) {
    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }
    private val cartAdapter: CartAdapter = CartAdapter(createAdapterOnClickHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSupportActionBar()
        initViewBinding()
        initObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.cart_title)
    }

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.cartProductItemsContainer.adapter = cartAdapter
        binding.cartProductItemsContainer.itemAnimator = null
    }

    private fun initObservers() {
        viewModel.cartProducts.observe(this) { products ->
            cartAdapter.submitItems(products.products)
        }
        viewModel.editedProductIds.observe(this) { editedProductIds ->
            setResult(
                ResultCode.CART_PRODUCT_EDITED.code,
                Intent().apply {
                    putIntegerArrayListExtra(ResultCode.CART_PRODUCT_EDITED.key, ArrayList(editedProductIds))
                },
            )
        }
    }

    private fun createAdapterOnClickHandler() =
        object : CartViewHolder.OnClickHandler {
            override fun onRemoveCartProductClick(id: Int) {
                viewModel.removeCartProduct(id)
            }

            override fun onIncreaseClick(id: Int) {
                viewModel.increaseCartProductQuantity(id)
            }

            override fun onDecreaseClick(id: Int) {
                viewModel.decreaseCartProductQuantity(id)
            }
        }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
