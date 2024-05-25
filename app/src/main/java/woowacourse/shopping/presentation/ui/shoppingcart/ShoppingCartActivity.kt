package woowacourse.shopping.presentation.ui.shoppingcart

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.app.ShoppingApplication
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.observeEvent
import woowacourse.shopping.presentation.ui.productlist.ProductListActivity
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.CartProductsAdapter

class ShoppingCartActivity : BaseActivity<ActivityShoppingCartBinding>() {
    override val layoutResourceId: Int get() = R.layout.activity_shopping_cart

    private val viewModel: ShoppingCartViewModel by viewModels {
        ShoppingCartViewModel.factory((application as ShoppingApplication).shoppingCartRepository)
    }

    private val adapter: CartProductsAdapter by lazy { CartProductsAdapter(viewModel, viewModel) }

    override fun initCreateView() {
        initActionBar()
        initDataBinding()
        initAdapter()
        initObserve()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.cart_title)
        }
    }

    private fun initDataBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ShoppingCartActivity
        }
    }

    private fun initAdapter() {
        binding.rvCartProducts.adapter = adapter
    }

    private fun initObserve() {
        viewModel.uiState.observe(this) { uiState ->
            adapter.updateCartProducts(uiState.pagingCartProduct.products)
        }

        viewModel.message.observeEvent(this) { message ->
            when (message) {
                is MessageProvider.DefaultErrorMessage -> showSnackbar(message.getMessage(this))
            }
        }

        viewModel.navigateAction.observeEvent(this) { navigateAction ->
            when (navigateAction) {
                is ShoppingCartNavigateAction.NavigateToProductList -> {
                    val resultIntent =
                        intent.apply {
                            putExtra(
                                ProductListActivity.PUT_EXTRA_UPDATED_PRODUCTS,
                                navigateAction.updatedProducts,
                            )
                        }

                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.navigateToProductList()
        return true
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        viewModel.navigateToProductList()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
