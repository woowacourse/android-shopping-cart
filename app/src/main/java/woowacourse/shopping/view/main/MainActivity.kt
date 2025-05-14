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

class MainActivity : AppCompatActivity(), ProductsAdapterEventHandler {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
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

    private fun initView() {
        val productsAdapter = ProductAdapter(handler = this)
        binding.buttonLoad.setOnClickListener {
            binding.buttonLoad.visibility = View.GONE
            val layoutManager =
                binding.recyclerViewProduct.layoutManager as? LinearLayoutManager
                    ?: throw IllegalArgumentException()

            val visiblePosition = layoutManager.itemCount
            val pageSize = 20
            val currentPage = visiblePosition / pageSize
            viewModel.loadProducts(currentPage, pageSize)
        }
        binding.recyclerViewProduct.apply {
            adapter = productsAdapter
            setHasFixedSize(true)
            setItemAnimator(null)
        }.addOnScrollListener(onScrollListener())

        viewModel.products.observe(this) { value ->
            productsAdapter.submitList(value)
        }

        viewModel.isLastPage.observe(this) { value ->
            if (value) binding.buttonLoad.visibility = View.GONE
        }
    }

    private fun onScrollListener() =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int,
            ) {
                val isLastPage = viewModel.isLastPage.value ?: return
                if (!isLastPage && !recyclerView.canScrollVertically(1)) {
                    binding.buttonLoad.visibility = View.VISIBLE
                } else if (dy < 1) {
                    binding.buttonLoad.visibility = View.GONE
                }
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_action_bar_menu, menu)
        return true
    }

    override fun onSelectProduct(productId: Long) {
        val intent = DetailActivity.newIntent(this, productId)
        startActivity(intent)
    }
}
