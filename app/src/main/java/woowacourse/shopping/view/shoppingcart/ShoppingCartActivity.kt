package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.utils.showToast
import woowacourse.shopping.view.DefaultQuantityControlListener

class ShoppingCartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShoppingCartBinding.inflate(layoutInflater) }
    private lateinit var viewModel: ShoppingCartViewModel

    private lateinit var adapter: SelectedProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.rootShoppingCart)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootShoppingCart) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel =
            ViewModelProvider(
                this,
                ShoppingCartViewModel.provideFactory(),
            )[ShoppingCartViewModel::class.java]

        initRecyclerView()
        initBindings()
        initObservers()
    }

    private fun initRecyclerView() {
        adapter =
            SelectedProductAdapter(
                DefaultQuantityControlListener(
                    onPlus = viewModel::addToShoppingCart,
                    onMinus = viewModel::removeFromShoppingCart,
                ),
            ) { product ->
                viewModel.deleteProduct(product)
            }
        binding.rvProducts.adapter = adapter
    }

    private fun initBindings() {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObservers() {
        viewModel.cacheShoppingCartProduct.observe(this) { value ->
            adapter.updateShoppingProductItems(value)
        }

        viewModel.hasNext.observe(this) { value ->
            binding.btnRight.isEnabled = value
        }

        viewModel.event.observe(this) { event: ShoppingCartEvent ->
            when (event) {
                ShoppingCartEvent.LOAD_SHOPPING_CART_FAILURE ->
                    showToast(R.string.shopping_cart_load_shopping_cart_error_message)

                ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE ->
                    showToast(R.string.shopping_cart_remove_shopping_cart_product_error_message)

                ShoppingCartEvent.PLUS_CART_ITEM_QUANTITY_FAILURE ->
                    showToast(R.string.shopping_cart_update_shopping_cart_quantity_error_message)

                ShoppingCartEvent.MINUS_CART_ITEM_QUANTITY_FAILURE ->
                    showToast(R.string.shopping_cart_update_shopping_cart_quantity_error_message)
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)
    }
}
