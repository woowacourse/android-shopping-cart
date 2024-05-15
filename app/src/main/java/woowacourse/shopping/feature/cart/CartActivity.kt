package woowacourse.shopping.feature.cart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.viewmodel.CartViewModel

class CartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private lateinit var adapter: CartAdapter
    private val cartViewModel: CartViewModel by viewModels()
    private val cartRepository: CartRepository by lazy { CartRepositoryImpl }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        initializeCartAdapter()
        initializeToolbar()
        updateCart()
    }

    private fun initializeCartAdapter() {
        adapter =
            CartAdapter(onClickExit = {
                cartViewModel.delete(cartRepository, it)
                cartViewModel.load(cartRepository)
            })
        binding.rvCart.adapter = adapter
    }

    private fun initializeToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            finish()
        }
    }

    private fun updateCart() {
        cartViewModel.cart.observe(this) {
            adapter.updateCart(it.map { cartItem -> ProductRepositoryImpl.find(cartItem.productId) })
        }
        cartViewModel.load(cartRepository)
    }
}
