package woowacourse.shopping.view.shoppingCart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.view.common.showToast

class ShoppingCartActivity :
    AppCompatActivity(),
    OnShoppingCartPaginationClickListener {

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
        binding.onClickBackButton = ::finish
    }

    private fun bindData() {
        viewModel.shoppingCart.observe(this) { shoppingCart: List<ShoppingCartItem> ->
            shoppingCartProductAdapter.submitList(shoppingCart)
        }
    }

    private fun handleEvents() {
        viewModel.event.observe(this) { event: ShoppingCartEvent ->
            @StringRes
            val messageResourceId: Int =
                when (event) {
                    ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE ->
                        R.string.shopping_cart_update_shopping_cart_error_message

                    ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE ->
                        R.string.shopping_cart_remove_shopping_cart_product_error_message
                }

            showToast(getString(messageResourceId))
        }
    }

    override fun onMinusPage() {
        viewModel.minusPage()
    }

    override fun onPlusPage() {
        viewModel.plusPage()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)
    }
}
