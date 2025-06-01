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
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.QuantityControlListener

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(application as ShoppingApplication)
    }

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
                        viewModel.deleteCartProduct(product.id)
                    },
                onPaginationButtonClick = viewModel::onPaginationButtonClick,
                onQuantityControl =
                    object : QuantityControlListener {
                        override fun onQuantityChanged(
                            buttonEvent: ButtonEvent,
                            product: ProductUiModel,
                        ) = viewModel.updateQuantity(buttonEvent, product)

                        override fun onAdd(product: ProductUiModel) = Unit
                    },
            )
    }

    private fun observeCartViewModel() {
        val cartAdapter = (binding.recyclerViewCart.adapter as CartAdapter)
        viewModel.cartProducts.observe(this, cartAdapter::setCartItems)
        viewModel.updatedItem.observe(this, cartAdapter::setCartItem)
        viewModel.isNextButtonEnabled.observe(this) { viewModel.updateButton() }
        viewModel.isPrevButtonEnabled.observe(this) { viewModel.updateButton() }
        viewModel.updateButton.observe(this, cartAdapter::setButton)
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
