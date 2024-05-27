package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.cart.adapter.CartAdapter
import woowacourse.shopping.presentation.ui.shopping.ShoppingActivity
import woowacourse.shopping.presentation.util.EventObserver

class CartActivity : BindingActivity<ActivityCartBinding>(), CartHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_cart

    private val cartAdapter: CartAdapter = CartAdapter(this)

    private val viewModel: CartViewModel by viewModels { ViewModelFactory() }

    override fun initStartView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.loadProductByPage(0)
        }
        binding.cartHandler = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initActionBarTitle()
        initCartAdapter()
        observeErrorEventUpdates()
        observeCartUpdates()
        observeChangedCartProducts()
    }

    private fun initActionBarTitle() {
        title = getString(R.string.cart_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initCartAdapter() {
        binding.rvCarts.adapter = cartAdapter
    }

    private fun observeErrorEventUpdates() {
        viewModel.error.observe(
            this,
            EventObserver {
                showToast(it.message)
            },
        )
    }

    private fun observeCartUpdates() {
        viewModel.shoppingProducts.observe(this) {
            when (it) {
                is UiState.None -> {}
                is UiState.Success -> handleSuccessState(it.data)
            }
        }
    }

    private fun handleSuccessState(products: List<ProductListItem.ShoppingProductItem>) {
        cartAdapter.updateList(products)
        viewModel.updatePageController()
    }

    private fun observeChangedCartProducts() {
        viewModel.changedCartProducts.observe(this) { carts ->
            carts?.let {
                Intent(applicationContext, ShoppingActivity::class.java).apply {
                    putExtra(EXTRA_CHANGED_PRODUCT_IDS, it.map { it.product.id }.toLongArray())
                    putExtra(EXTRA_NEW_PRODUCT_QUANTITIES, it.map { it.quantity }.toIntArray())
                    setResult(RESULT_OK, this)
                }
            }
        }
    }

    override fun onDeleteClick(product: ProductListItem.ShoppingProductItem) {
        viewModel.deleteProduct(product.toProduct())
    }

    override fun onNextPageClick() {
        viewModel.turnNextPage()
    }

    override fun onBeforePageClick() {
        viewModel.turnPreviousPage()
    }

    override fun onDecreaseQuantity(item: ProductListItem.ShoppingProductItem?) {
        item?.let {
            viewModel.updateCartItemQuantity(
                item.toProduct(),
                -1,
            )
        }
    }

    override fun onIncreaseQuantity(item: ProductListItem.ShoppingProductItem?) {
        item?.let {
            viewModel.updateCartItemQuantity(
                item.toProduct(),
                1,
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_CHANGED_PRODUCT_IDS = "changedProductIds"
        const val EXTRA_NEW_PRODUCT_QUANTITIES = "newProductQuantities"

        fun startWithResult(
            context: Context,
            activityLauncher: ActivityResultLauncher<Intent>,
        ) {
            Intent(context, CartActivity::class.java).apply {
                activityLauncher.launch(this)
            }
        }
    }
}
