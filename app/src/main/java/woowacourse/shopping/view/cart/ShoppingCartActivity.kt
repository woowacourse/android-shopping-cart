package woowacourse.shopping.view.cart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.cart.adapter.CartProductAdapter
import woowacourse.shopping.view.cart.adapter.CartProductEventHandler

class ShoppingCartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShoppingCartBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ShoppingCartViewModel.provideFactory(applicationContext),
        )[ShoppingCartViewModel::class.java]
    }

    private lateinit var adapter: CartProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        initRecyclerView()
        initBindings()
        initObservers()
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
        adapter =
            CartProductAdapter(
                eventHandler =
                    object : CartProductEventHandler {
                        override fun onItemRemoveClick(product: ShoppingProduct) {
                            viewModel.deleteProduct(product)
                        }
                    },
            )
        binding.rvProducts.adapter = adapter
    }

    private fun initBindings() {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.handler =
            object : ShoppingCartEventHandler {
                override fun onPreviousPageClick() {
                    viewModel.loadPreviousProducts()
                }

                override fun onNextPageClick() {
                    viewModel.loadNextProducts()
                }
            }
    }

    private fun initObservers() {
        viewModel.removedProduct.observe(this) { value ->
            adapter.removeItem(value)
        }

        viewModel.product.observe(this) { value ->
            adapter.updateItems(value)
        }
    }
}
