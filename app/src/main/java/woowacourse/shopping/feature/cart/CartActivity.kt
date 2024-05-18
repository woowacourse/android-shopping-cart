package woowacourse.shopping.feature.cart

import android.os.Bundle
import android.view.View
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
    private var page: Int = 0
    private var cartSize: Int = 0

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
            CartAdapter(onClickExit = { deleteCartItem(it) })
        binding.rvCart.adapter = adapter

        cartViewModel.cartSize.observe(this) {
            cartSize = it
            updatePageLayout(it)
        }

        cartViewModel.cart.observe(this) {
            adapter.updateCart(it)
        }
    }

    private fun deleteCartItem(it: Product) {
        if (isEmptyLastPage()) page--
        cartViewModel.delete(it)
        updateCart()
    }

    private fun updatePageLayout(it: Int) {
        binding.layoutPage.visibility = if (it > PAGE_SIZE) View.VISIBLE else View.GONE
        binding.btnCartPreviousPage.isEnabled = hasPreviousPage(page, MIN_PAGE)
        binding.btnCartNextPage.isEnabled = hasNextPage(page, (it - 1) / PAGE_SIZE)
    }

    private fun isEmptyLastPage() = page > 0 && cartSize % PAGE_SIZE == 1

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

    private fun updateCart() {
        cartViewModel.loadCart(page, PAGE_SIZE)
        cartViewModel.loadCount()
    }

    private fun initializeToolbar() {
        binding.toolbarCart.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        private const val MIN_PAGE = 0
        private const val PAGE_SIZE = 5
    }
}
