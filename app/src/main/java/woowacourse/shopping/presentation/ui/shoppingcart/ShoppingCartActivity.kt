package woowacourse.shopping.presentation.ui.shoppingcart

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.observeEvent
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.OrderListAdapter

class ShoppingCartActivity : BaseActivity<ActivityShoppingCartBinding>() {
    override val layoutResourceId: Int get() = R.layout.activity_shopping_cart

    private val viewModel: ShoppingCartViewModel by viewModels {
        shoppingApplication.shoppingCartViewModelFactory()
    }

    private val adapter: OrderListAdapter by lazy { OrderListAdapter(viewModel) }

    override fun initStartView() {
        initViewModel()
        initActionBar()
        initDataBinding()
        initAdapter()
        initObserve()
    }

    private fun initViewModel() {
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
        binding.rvOrderList.adapter = adapter
    }

    private fun initObserve() {
        viewModel.uiState.observe(this) { uiState ->
            uiState.pagingOrder?.let { pagingOrder ->
                adapter.updateOrderList(pagingOrder.orderList)
            }
        }

        viewModel.message.observeEvent(this) { message ->
            when (message) {
                is MessageProvider.DefaultErrorMessage -> showSnackbar(message.getMessage(this))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ShoppingCartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
