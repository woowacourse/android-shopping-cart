package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.view.cart.adapter.CartProductAdapter

class ShoppingCartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShoppingCartBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        val app = application as ShoppingApplication
        ViewModelProvider(
            this,
            ShoppingCartViewModelFactory(app.cartProductRepository),
        )[ShoppingCartViewModel::class.java]
    }

    private lateinit var adapter: CartProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        initRecyclerView()
        initBindings()
        initObservers()

        supportActionBar?.title = ACTION_BAR_TITLE
    }

    private fun setUpView() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initRecyclerView() {
        adapter = CartProductAdapter(eventHandler = viewModel)
        binding.rvProducts.adapter = adapter
        binding.rvProducts.itemAnimator = null
    }

    private fun initBindings() {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.handler = viewModel
    }

    private fun initObservers() {
        viewModel.products.observe(this) { value ->
            adapter.updateItems(value)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ShoppingCartActivity::class.java)

        private const val ACTION_BAR_TITLE = "Cart"
    }
}
