package woowacourse.shopping.feature.cart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.feature.cart.viewmodel.CartViewModel
import woowacourse.shopping.feature.cart.viewmodel.CartViewModelFactory

class CartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private val cartViewModel: CartViewModel by viewModels { CartViewModelFactory(CartDummyRepository) }
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
            finish()
        }
    }

    private fun initializeCartAdapter() {
        adapter =
            CartAdapter(
                onClickExit = { deleteCartItem(cartItemId = it) },
                onClickPlusButton = { cartViewModel.addProduct(it) },
                onClickMinusButton = { cartViewModel.deleteProduct(it) },
            )
        binding.rvCart.adapter = adapter

        cartViewModel.cart.observe(this) {
            adapter.updateCart(it)
        }
    }

    private fun deleteCartItem(cartItemId: Long) {
        cartViewModel.checkEmptyLastPage()
        cartViewModel.isEmptyLastPage.observe(this) { isEmptyLastPage ->
            if (isEmptyLastPage) cartViewModel.decreasePage()
        }
        cartViewModel.deleteCartItem(cartItemId)
    }
}
