package woowacourse.shopping.feature.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.feature.cart.viewmodel.CartViewModel
import woowacourse.shopping.feature.cart.viewmodel.CartViewModelFactory
import woowacourse.shopping.feature.main.MainActivity.Companion.UPDATE_CART_STATUS_KEY

class CartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(CartDummyRepository(CartDatabase.initialize(this).cartDao()))
    }
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeBinding()
        initializeView()
    }

    private fun initializeBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = cartViewModel
    }

    private fun initializeView() {
        initializeToolbar()
        initializeCartAdapter()
    }

    private fun initializeToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(UPDATE_CART_STATUS_KEY, true)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun initializeCartAdapter() {
        adapter =
            CartAdapter(
                onClickExit = { cartViewModel.deleteCartItem(productId = it) },
                onClickPlusButton = { cartViewModel.addProduct(productId = it) },
                onClickMinusButton = { cartViewModel.deleteProduct(productId = it) },
            )
        binding.rvCart.adapter = adapter

        cartViewModel.cart.observe(this) {
            adapter.updateCart(it)
        }
    }
}
