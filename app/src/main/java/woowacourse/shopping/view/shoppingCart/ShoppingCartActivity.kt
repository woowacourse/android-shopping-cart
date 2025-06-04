package woowacourse.shopping.view.shoppingCart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.ResultFrom
import woowacourse.shopping.view.common.showSnackBar

class ShoppingCartActivity :
    AppCompatActivity(),
    ShoppingCartListener {
    private val viewModel: ShoppingCartViewModel by viewModels()
    private val binding: ActivityShoppingCartBinding by lazy {
        ActivityShoppingCartBinding.inflate(layoutInflater)
    }
    private val shoppingCartProductAdapter by lazy {
        ShoppingCartProductAdapter(viewModel::removeShoppingCartProduct, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.shoppingCartRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initDataBinding()
        bindData()
        handleEvents()

        viewModel.updateShoppingCart()
    }

    private fun initDataBinding() {
        binding.adapter = shoppingCartProductAdapter
        binding.lifecycleOwner = this
        binding.shoppingCartListener = this
    }

    private fun bindData() {
        viewModel.shoppingCart.observe(this) { shoppingCart: List<ShoppingCartItem> ->
            shoppingCartProductAdapter.submitList(shoppingCart)
        }
    }

    private fun handleEvents() {
        viewModel.event.observe(this) { event: ShoppingCartEvent ->
            when (event) {
                ShoppingCartEvent.CartDecreasingFailed ->
                    binding.root.showSnackBar(
                        getString(R.string.products_minus_shopping_cart_product_error_message),
                    )

                ShoppingCartEvent.CartFetchFailed ->
                    binding.root.showSnackBar(
                        getString(R.string.shopping_cart_update_shopping_cart_error_message),
                    )

                ShoppingCartEvent.CartIncreasingFailed ->
                    binding.root.showSnackBar(
                        getString(R.string.product_detail_add_shopping_cart_error_message),
                    )

                ShoppingCartEvent.CartRemovedFailed ->
                    binding.root.showSnackBar(
                        getString(R.string.shopping_cart_remove_shopping_cart_product_error_message),
                    )

                is ShoppingCartEvent.UpdatedProductRequested -> {
                    val intent =
                        Intent().apply {
                            putExtra(
                                "updateProducts",
                                viewModel.updatedProducts.value?.toTypedArray(),
                            )
                        }
                    setResult(ResultFrom.SHOPPING_CART_BACK.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    override fun onMinusPage() {
        viewModel.minusPage()
    }

    override fun onPlusPage() {
        viewModel.plusPage()
    }

    override fun onRemoveButton(product: Product) {
        viewModel.removeShoppingCartProduct(product)
    }

    override fun onPlusShoppingCartClick(product: Product) {
        viewModel.addQuantity(product)
    }

    override fun onMinusShoppingCartClick(product: Product) {
        viewModel.decreaseQuantity(product)
    }

    override fun onBackButtonClick() {
        viewModel.updateProductsRequest()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)
    }
}
