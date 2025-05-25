package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.detail.DetailActivity
import woowacourse.shopping.view.main.adapter.ProductAdapter
import woowacourse.shopping.view.main.adapter.RecentProductAdapter
import woowacourse.shopping.view.main.event.ProductsAdapterEventHandler
import woowacourse.shopping.view.main.vm.MainViewModel
import woowacourse.shopping.view.main.vm.MainViewModelFactory
import woowacourse.shopping.view.util.QuantitySelectorEventHandler
import woowacourse.shopping.view.util.scroll.ScrollEndEvent
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private val productsAdapter: ProductAdapter by lazy {
        ProductAdapter(
            quantitySelectorEventHandler = quantitySelectorEventHandler,
            handler = productsAdapterEventHandler,
        )
    }

    private val recentProductAdapter: RecentProductAdapter by lazy {
        RecentProductAdapter()
    }

    private lateinit var binding: ActivityMainBinding

    private val productsAdapterEventHandler =
        object : ProductsAdapterEventHandler {
            override fun onSelectProduct(cart: Cart) {
                val intent = DetailActivity.newIntent(this@MainActivity, cart.product.id)
                startActivity(intent)
            }

            override fun onAddCart(cart: Cart) {
                viewModel.addCart(cart)
            }
        }

    private val quantitySelectorEventHandler =
        object : QuantitySelectorEventHandler {
            override fun onQuantityMinus(cart: Cart) {
                viewModel.minusCartQuantity(cart)
            }

            override fun onQuantityPlus(cart: Cart) {
                viewModel.plusCartQuantity(cart)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.productAdapter = productsAdapter
        binding.recentProductAdapter = recentProductAdapter
        binding.vm = viewModel
        initView()
        observeViewModel()
    }

    private fun initView() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        viewModel.loadProducts()
        viewModel.loadRecentProducts()
        val scrollEndEvent =
            ScrollEndEvent(
                onScrollEnd = {
                    if (viewModel.loadable.value == true) {
                        binding.buttonLoad.visibility = View.VISIBLE
                    }
                },
                onScrollReset = { binding.buttonLoad.visibility = View.GONE },
            )
        binding.recyclerViewProduct.apply {
            adapter = productsAdapter
            setHasFixedSize(true)
            setItemAnimator(null)
            addOnScrollListener(scrollEndEvent)
        }

        binding.buttonLoad.setOnClickListener {
            binding.buttonLoad.visibility = View.GONE
            viewModel.moveNextPage()
        }
        binding.cartIcon.setOnClickListener {
            val intent = CartActivity.newIntent(this)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.carts.observe(this) { value ->
            productsAdapter.submitList(value)
        }
        viewModel.recentProducts.observe(this) { value ->
            recentProductAdapter.submitList(value)
        }
    }
}
