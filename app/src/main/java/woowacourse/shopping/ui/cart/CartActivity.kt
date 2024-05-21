package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.ui.cart.adapter.CartAdapter
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel
import woowacourse.shopping.ui.cart.viewmodel.CartViewModelFactory

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val viewModel: CartViewModel by viewModels { CartViewModelFactory(CartsImpl) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initToolbar()
        setCartAdapter()
        observeCartItems()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observeCartItems() {
        viewModel.cart.observe(this) {
            adapter.setData(it)
        }
    }

    private fun setCartAdapter() {
        binding.rvCart.itemAnimator = null
        adapter =
            CartAdapter(
                { cartId -> viewModel.removeCartItem(cartId) },
                { cartId -> viewModel.plusCount(cartId) },
                { cartId -> viewModel.minusCount(cartId) },
            )

        binding.rvCart.adapter = adapter
    }

    companion object {
        fun startActivity(context: Context) =
            Intent(context, CartActivity::class.java).run {
                context.startActivity(this)
            }
    }
}
