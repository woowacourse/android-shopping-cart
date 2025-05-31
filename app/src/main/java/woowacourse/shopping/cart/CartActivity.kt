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
import woowacourse.shopping.cart.event.CartEventHandlerImpl
import woowacourse.shopping.data.cart.CartItemDatabase
import woowacourse.shopping.data.cart.CartItemRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by lazy { createViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        setupWindowInsets()
        setupToolbar()
        initBinding()
        initRecyclerView()
        observeCartViewModel()
    }

    private fun createViewModel(): CartViewModel {
        val db = CartItemDatabase.getInstance(this)
        val repository = CartItemRepositoryImpl(db.cartItemDao())
        return ViewModelProvider(this, factory(repository))[CartViewModel::class.java]
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.text_cart_action_bar)
        }
    }

    private fun initRecyclerView() {
        val handler = CartEventHandlerImpl(viewModel)
        binding.recyclerViewCart.adapter =
            CartAdapter(
                cartProducts = emptyList(),
                cartHandler = handler,
                handler = handler,
            )
    }

    private fun observeCartViewModel() {
        val adapter = getCartAdapter()

        viewModel.pagingData.observe(this) {
            adapter.setData(it.products)
            adapter.setPagination()
        }

        viewModel.product.observe(this) {
            adapter.updateProduct(it)
        }
    }

    private fun getCartAdapter(): CartAdapter = binding.recyclerViewCart.adapter as CartAdapter

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }
}
