package woowacourse.shopping.presentation.cart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.cart.CartViewModel
import woowacourse.shopping.util.DatabaseProvider

class CartActivity : BindingActivity<ActivityCartBinding>(R.layout.activity_cart) {
    private val viewModel: CartViewModel by viewModels {
        provideCartViewModelFactory(this)
    }

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartAdapter = CartAdapter(createAdapterOnClickHandler())

        initSupportActionBar()
        initViewBinding()
        initObservers()
    }

    private fun createAdapterOnClickHandler() = object : CartViewHolder.OnClickHandler {
        override fun onRemoveCartProductClick(id: Int) {
            viewModel.removeCartProduct(id)
        }

        override fun onIncreaseCount(product: CartProduct) {
            viewModel.increaseCount(product)
        }

        override fun onDecreaseCount(product: CartProduct) {
            viewModel.decreaseCount(product)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun initSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.cart_title)
    }

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvCart.adapter = cartAdapter
    }

    private fun initObservers() {
        viewModel.products.observe(this) { products ->
            cartAdapter.replaceItems(products)
        }
    }


    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}

fun provideCartViewModelFactory(context: Context): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val appContext = context.applicationContext

            val cartDao = DatabaseProvider.getDatabase(appContext).cartDao()
            val productDao = DatabaseProvider.getDatabase(appContext).productDao()

            val cartRepository = CartRepositoryImpl(cartDao, productDao)

            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepository) as T
        }
    }
}