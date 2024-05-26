package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.datasourceimpl.DefaultCartDataSource
import woowacourse.shopping.data.datasourceimpl.DefaultProductDataSource
import woowacourse.shopping.data.repository.DefaultCartRepository
import woowacourse.shopping.data.repository.DefaultProductRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.data.db.cart.CartDatabase
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
            adapter.updateOrder(it)
        }
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                CartViewModelFactory(
                    DefaultCartRepository(
                        DefaultCartDataSource(
                            CartDatabase.getInstance(this),
                        ),
                    ),
                    DefaultProductRepository(DefaultProductDataSource),
                ),
            )[CartViewModel::class.java]
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.cartViewModel = viewModel
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
            android.R.id.home -> {
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CART_ITEMS, viewModel.productIds.value?.toLongArray())
                setResult(CART_RESULT_OK, resultIntent)

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCartItemDelete(cartItemId: Long) {
        viewModel.removeAllCartItem(cartItemId)
    }

    companion object {
        const val EXTRA_CART_ITEMS = "extra_cart_items"
        const val CART_RESULT_OK = 2000

        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
