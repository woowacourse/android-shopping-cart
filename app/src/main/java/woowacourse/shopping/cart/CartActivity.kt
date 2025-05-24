package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.cart.CartItem.PaginationButtonItem
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.product.catalog.ProductUiModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        applyWindowInsets()
        setViewModel()
        setSupportActionBar()
        setCartProductAdapter()
        observeCartViewModel()
    }

    private fun setViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                CartViewModelFactory(application as ShoppingApplication),
            )[CartViewModel::class.java]
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
                quantityControlListener = viewModel::updateQuantity,
            )
    }

    private fun observeCartViewModel() {
        viewModel.cartProducts.observe(this) { updateCartItems() }
        viewModel.isNextButtonEnabled.observe(this) { updateCartItems() }
        viewModel.isPrevButtonEnabled.observe(this) { updateCartItems() }
        viewModel.page.observe(this) { updateCartItems() }
        viewModel.updatedItem.observe(this) { item ->
            (binding.recyclerViewCart.adapter as CartAdapter).setCartItem(item)
        }
    }

    private fun updateCartItems() {
        val products: List<ProductUiModel> = viewModel.cartProducts.value ?: return
        val isNext: Boolean = viewModel.isNextButtonEnabled.value == true
        val isPrev: Boolean = viewModel.isPrevButtonEnabled.value == true
        val page: Int = viewModel.page.value ?: 0
        val paginationItem = PaginationButtonItem(page + 1, isNext, isPrev)

        val cartItems: List<CartItem> = products.map { CartItem.ProductItem(it) }
        val cartItemsWithPaginationBtn =
            if (cartItems.isEmpty()) cartItems else cartItems + paginationItem
        (binding.recyclerViewCart.adapter as CartAdapter).setCartItems(cartItemsWithPaginationBtn)
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
