package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductsImpl

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory(CartsImpl, ProductsImpl, this.applicationContext))
            .get(CartViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        loadItems()
        setCartAdapter()

        observeCartItems()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadItems() {
        viewModel.loadCartItems()
    }

    private fun observeCartItems() {
        viewModel.cart.observe(this) {
            adapter.setData(it.values.toList())
        }
    }

    private fun setCartAdapter() {
        adapter =
            CartAdapter(viewModel, this) { productId ->
                viewModel.removeCartItem(productId)
            }
        binding.rvCart.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun startActivity(context: Context) =
            Intent(context, CartActivity::class.java).run {
                context.startActivity(this)
            }
    }
}
