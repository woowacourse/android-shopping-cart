package woowacourse.shopping.feature.cart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.feature.cart.adapter.CartAdapter
import woowacourse.shopping.feature.cart.viewmodel.CartViewModel
import woowacourse.shopping.feature.cart.viewmodel.CartViewModelFactory
import woowacourse.shopping.model.Product

class CartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private val cartViewModel: CartViewModel by viewModels { CartViewModelFactory(CartDummyRepository, ProductDummyRepository) }
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
        updatePageLayout()
    }

    private fun initializeToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initializeCartAdapter() {
        adapter =
            CartAdapter(onClickExit = { deleteCartItem(it) })
        binding.rvCart.adapter = adapter

        cartViewModel.cart.observe(this) {
            adapter.updateCart(it)
        }
    }

    private fun deleteCartItem(product: Product) {
        cartViewModel.checkEmptyLastPage()
        cartViewModel.isEmptyLastPage.observe(this) { isEmptyLastPage ->
            if (isEmptyLastPage) cartViewModel.decreasePage()
        }
        cartViewModel.delete(product)
    }

    private fun updatePageLayout() {
        cartViewModel.cartSize.observe(this) {
            cartViewModel.checkOnlyOnePage()
            cartViewModel.hasPreviousPage()
            cartViewModel.hasNextPage()
        }
    }
}
