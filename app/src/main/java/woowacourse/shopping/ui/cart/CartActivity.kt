package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartAdapter

class CartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CartViewModel> { CartViewModelFactory(CartRepository.getInstance()) }
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initializeView()
    }

    private fun initializeView() {
        initializeToolbar()
        initializeCartAdapter()
    }

    private fun initializeToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initializeCartAdapter() {
        adapter = CartAdapter(
            onClickExit = { viewModel.deleteCartItem(it) },
            onIncreaseProductQuantity = { viewModel.increaseQuantity(it) },
            onDecreaseProductQuantity = { viewModel.decreaseQuantity(it) },
        )
        binding.rvCart.adapter = adapter

        viewModel.cart.observe(this) {
            adapter.changeCartItems(it)
        }

        viewModel.changedCartItemQuantity.observe(this) {
            adapter.replaceCartItem(it)
        }

        viewModel.removedCartItemId.observe(this) {
            adapter.removeCartItemById(it)
        }
    }
}
