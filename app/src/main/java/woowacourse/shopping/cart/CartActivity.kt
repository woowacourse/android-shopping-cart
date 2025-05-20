package woowacourse.shopping.cart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartViewModel.Companion.factory
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            factory(CartDatabase),
        )[CartViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        applyWindowInsets()

        setSupportActionBar()
        setCartProductAdapter()
        observeCartProducts()
        observePageChanges()
    }

    private fun setSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_cart_action_bar)
    }

    private fun setCartProductAdapter() {
        val handler = CartEventHandlerImpl(viewModel)
        binding.recyclerViewCart.adapter =
            CartAdapter(
                cartProducts = emptyList(),
                handler = handler,
            )
    }

    private fun observeCartProducts() {
        viewModel.cartProducts.observe(this) { value ->
            (binding.recyclerViewCart.adapter as CartAdapter).setData(value)
        }
        binding.lifecycleOwner = this
    }

    private fun observePageChanges() {
        viewModel.page.observe(this) {
            (binding.recyclerViewCart.adapter as? CartAdapter)?.let { adapter ->
                val paginationPos = adapter.itemCount - 1
                adapter.notifyItemChanged(paginationPos)
            }
        }
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
}
