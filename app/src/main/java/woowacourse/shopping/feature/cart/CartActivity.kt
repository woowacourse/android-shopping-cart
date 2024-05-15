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
    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        binding.page = page
        initializeCartAdapter()
        initializePageButton()
        initializeToolbar()
        updateCart()
    }

    private fun initializeCartAdapter() {
        adapter =
            CartAdapter(onClickExit = {
                cartViewModel.delete(cartRepository, it)
                updateCart()
            })
        binding.rvCart.adapter = adapter

        cartViewModel.cartSize.observe(this) {
            binding.btnCartPreviousPage.isEnabled = hasPreviousPage(page, MIN_PAGE)
            binding.btnCartNextPage.isEnabled = hasNextPage(page, (it - 1) / PAGE_SIZE)
        }

        cartViewModel.cart.observe(this) {
            adapter.updateCart(it.map { cartItem -> ProductRepositoryImpl.find(cartItem.productId) })
        }
    }

    private fun hasPreviousPage(
        page: Int,
        minPage: Int = MIN_PAGE,
    ): Boolean = page > minPage

    private fun hasNextPage(
        page: Int,
        maxPage: Int,
    ): Boolean = page < maxPage

    private fun initializePageButton() {
        binding.btnCartNextPage.setOnClickListener {
            page++
            binding.page = page
            updateCart()
        }
        binding.btnCartPreviousPage.setOnClickListener {
            page--
            binding.page = page
            updateCart()
        }
    }

    private fun initializeToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            finish()
        }
    }

    private fun updateCart() {
        cartViewModel.loadCart(cartRepository, page, PAGE_SIZE)
        cartViewModel.loadCount(cartRepository)
    }

    companion object {
        private const val MIN_PAGE = 0
        private const val PAGE_SIZE = 5
    }
}
