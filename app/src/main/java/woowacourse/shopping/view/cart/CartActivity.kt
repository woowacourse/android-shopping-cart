package woowacourse.shopping.view.cart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.view.observeToastMessage
import woowacourse.shopping.view.products.QuantitySelectButtonListener

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val cartViewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        binding.viewModel = cartViewModel
        initRecyclerView()
        observeLoadedProducts()
        observeFinishCartButton()
        observeToastMessage(cartViewModel.toastMessage, this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()
        cartViewModel.updateQuantity()
    }

    private fun initRecyclerView() {
        adapter =
            CartAdapter(
                onProductRemoveClickListener = { cartItem ->
                    cartViewModel.removeFromCart(
                        cartItem,
                    )
                },
                quantitySelectButtonListener =
                    object : QuantitySelectButtonListener {
                        override fun increase(productId: Long) {
                            cartViewModel.increaseQuantity(productId)
                        }

                        override fun decrease(productId: Long) {
                            cartViewModel.decreaseQuantity(productId)
                        }
                    },
            )
        binding.rvProductsInCart.adapter = adapter
    }

    private fun observeFinishCartButton() {
        cartViewModel.finishCart.observe(this) {
            finish()
        }
    }

    private fun observeLoadedProducts() {
        cartViewModel.cartItems.observe(this) {
            adapter.updateProductsView(it)
        }
    }
}
