package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.datasource.DefaultCart
import woowacourse.shopping.data.datasource.DefaultProducts
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(), CartItemDeleteClickListener {
    private val binding: ActivityCartBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_cart)
    }
    private val viewModel: CartViewModel by lazy {
        ViewModelProvider(
            this,
            CartViewModelFactory(
                CartRepositoryImpl(DefaultCart),
                ProductRepositoryImpl(DefaultProducts),
            ),
        )[CartViewModel::class.java]
    }
    private val adapter: CartAdapter by lazy {
        CartAdapter(emptyList(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.adapter = adapter

        viewModel.loadCurrentPageCartItems()
        viewModel.orders.observe(this, adapter::replaceOrders)

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
        viewModel.removeCartItem(cartItemId)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
