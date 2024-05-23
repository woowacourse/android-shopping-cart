package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.datasource.DefaultCart
import woowacourse.shopping.data.datasource.DefaultProducts
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.presentation.cart.adapter.CartAdapter
import woowacourse.shopping.presentation.cart.adapter.CartItemClickListener
import woowacourse.shopping.presentation.cart.viewmodel.CartViewModel
import woowacourse.shopping.presentation.cart.viewmodel.CartViewModelFactory

class CartActivity : AppCompatActivity(), CartItemClickListener {
    private val binding: ActivityCartBinding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: CartViewModel
    private val adapter: CartAdapter by lazy {
        CartAdapter(this, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewModel()
        initBinding()
        initObserver()
        initToolBar()
    }

    private fun initObserver() {
        viewModel.orders.observe(this, adapter::replaceOrders)

        viewModel.updateOrder.observe(this) {
            adapter.updateOrder(it.product.id, it)
        }
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                CartViewModelFactory(
                    CartRepositoryImpl(DefaultCart),
                    ProductRepositoryImpl(DefaultProducts),
                ),
            )[CartViewModel::class.java]
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.adapter = adapter
    }

    private fun initToolBar() {
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCartItemDelete(cartItemId: Long) {
        viewModel.removeAllCartItem(cartItemId)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
