package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.detail.DetailActivity
import woowacourse.shopping.view.main.adapter.ProductAdapter
import woowacourse.shopping.view.main.adapter.ProductsAdapterEventHandler
import woowacourse.shopping.view.main.vm.MainViewModel
import woowacourse.shopping.view.main.vm.MainViewModelFactory
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private val productsAdapter: ProductAdapter by lazy {
        ProductAdapter { productId ->
            val intent = DetailActivity.newIntent(this, productId)
            startActivity(intent)
        }
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.adapter = productsAdapter
        viewModel.loadProducts(0, PAGE_SIZE)

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

        binding.recyclerViewProduct.apply {
            adapter = productsAdapter
            setHasFixedSize(true)
            setItemAnimator(null)
        }.addOnScrollListener(onScrollListener())

        binding.buttonLoad.setOnClickListener {
            binding.buttonLoad.visibility = View.GONE
            val layoutManager = binding.recyclerViewProduct.layoutManager as? LinearLayoutManager

            val visiblePosition = layoutManager?.itemCount
            viewModel.loadProducts(visiblePosition ?: 0, PAGE_SIZE)
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(this) { value ->
            productsAdapter.submitList(value)
        }

        viewModel.loadable.observe(this) { value ->
            if (!value) binding.buttonLoad.visibility = View.GONE
        }
    }

    private fun onScrollListener() =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int,
            ) {
                val loadable = viewModel.loadable.value ?: return
                if (!loadable && !recyclerView.canScrollVertically(RECYCLER_VIEW_END_POSITION)) {
                    binding.buttonLoad.visibility = View.VISIBLE
                } else if (dy < RECYCLER_VIEW_END_POSITION) {
                    binding.buttonLoad.visibility = View.GONE
                }
            }
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

    companion object {
        const val PAGE_SIZE = 20
        private const val RECYCLER_VIEW_END_POSITION = 1
    }
}
