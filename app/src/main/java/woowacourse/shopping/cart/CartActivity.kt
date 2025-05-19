package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartItem.PaginationButtonItem
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.product.catalog.ProductUiModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        applyWindowInsets()
        setSupportActionBar()
        setCartProductAdapter()
        observeCartViewModel()
    }

    private fun setSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_cart_action_bar)
    }

    private fun setCartProductAdapter() {
        binding.recyclerViewCart.adapter =
            CartAdapter(
                cartItems = emptyList(),
                onDeleteProductClick =
                    DeleteProductClickListener { product ->
                        viewModel.deleteCartProduct(CartItem.ProductItem(product))
                    },
                onPaginationButtonClick = viewModel::onPaginationButtonClick,
            )
    }

    private fun observeCartViewModel() {
        viewModel.cartProducts.observe(this) { updateCartItems() }
        viewModel.isNextButtonEnabled.observe(this) { updateCartItems() }
        viewModel.isPrevButtonEnabled.observe(this) { updateCartItems() }
        viewModel.page.observe(this) { updateCartItems() }
    }

    private fun updateCartItems() {
        val products: List<ProductUiModel> = viewModel.cartProducts.value ?: return
        val isNext: Boolean = viewModel.isNextButtonEnabled.value == true
        val isPrev: Boolean = viewModel.isPrevButtonEnabled.value == true
        val page: Int = viewModel.page.value ?: 0
        val paginationItem = PaginationButtonItem(page + 1, isNext, isPrev)

        val cartItems: List<CartItem> = products.map { CartItem.ProductItem(it) } + paginationItem
        (binding.recyclerViewCart.adapter as CartAdapter).setCartItems(cartItems)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
