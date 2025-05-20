package woowacourse.shopping.view.cart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val cartViewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        binding.viewModel = cartViewModel
        initRecyclerView()
        observeLoadedItems()
        observeProducts()
        setButtonsClickListener()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initRecyclerView() {
        adapter =
            CartAdapter(onProductRemoveClickListener = { product ->
                cartViewModel.removeToCart(
                    product,
                )
            })
        binding.rvProductsInCart.adapter = adapter
    }

    private fun setButtonsClickListener() {
        binding.backImageBtn.setOnClickListener {
            finish()
        }

        binding.btnPreviousPage.setOnClickListener {
            cartViewModel.loadPreviousPage()
        }

        binding.btnNextPage.setOnClickListener {
            cartViewModel.loadNextPage()
        }
    }

    private fun observeProducts() {
        cartViewModel.products.observe(this) {
            adapter.updateProductsView(it)
        }
    }

    private fun observeLoadedItems() {
        cartViewModel.loadedProducts.observe(this) {
            adapter.updateProductsView(it)
        }
    }
}
