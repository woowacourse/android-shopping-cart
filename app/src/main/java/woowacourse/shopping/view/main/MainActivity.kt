package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.App
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.detail.DetailActivity
import woowacourse.shopping.view.main.adapter.ProductAdapter
import woowacourse.shopping.view.main.adapter.ProductRvItems
import woowacourse.shopping.view.main.adapter.ProductsAdapterEventHandler
import woowacourse.shopping.view.main.vm.MainViewModel
import woowacourse.shopping.view.main.vm.MainViewModelFactory
import kotlin.getValue

class MainActivity : AppCompatActivity(), ProductsAdapterEventHandler {
    private val viewModel: MainViewModel by viewModels {
        val container = (application as App).container
        MainViewModelFactory(
            container.productRepository,
            container.cartRepository,
        )
    }
    private val productsAdapter: ProductAdapter by lazy {
        ProductAdapter(emptyList(), this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        with(binding) {
            lifecycleOwner = this@MainActivity
            adapter = productsAdapter
        }
        setUpSystemBar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setUpSystemBar() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() =
        with(binding.recyclerViewProduct) {
            adapter = productsAdapter
            layoutManager =
                GridLayoutManager(this@MainActivity, 2).apply {
                    spanSizeLookup = setSpanSizeLookup()
                }
            setHasFixedSize(true)
            itemAnimator = null
        }

    private fun setSpanSizeLookup() =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (productsAdapter.getItemViewType(position)) {
                    ProductRvItems.ViewType.VIEW_TYPE_PRODUCT.ordinal -> 1
                    ProductRvItems.ViewType.VIEW_TYPE_LOAD.ordinal -> 2
                    else -> throw IllegalArgumentException()
                }
            }
        }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { value ->
            productsAdapter.submitList(value)
        }
    }

    override fun onSelectProduct(productId: Long) {
        val intent = DetailActivity.newIntent(this, productId)
        startActivity(intent)
    }

    override fun onLoadMoreItems() {
        viewModel.loadProducts()
    }

    override fun showQuantity(productId: Long) {
        viewModel.increaseQuantity(productId)
    }

    override fun onClickIncrease(productId: Long) {
        viewModel.increaseQuantity(productId)
    }

    override fun onClickDecrease(productId: Long) {
        viewModel.decreaseQuantity(productId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bar_cart -> {
                val intent = CartActivity.newIntent(this)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_action_bar_menu, menu)
        return true
    }
}
