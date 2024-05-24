package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductContentsBinding
import woowacourse.shopping.databinding.MenuItemLayoutBinding
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.ProductDetailActivity

class ProductContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductContentsBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, ProductContentsViewModelFactory(ProductsImpl, this.applicationContext))
            .get(ProductContentsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductContentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProductAdapter()
        observeProductItems()
        observeRecentProduct()
        setOnLoadMoreButtonClickListener()
        setOnRecyclerViewScrollListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProducts()
        viewModel.loadRecentProducts()
    }

    private fun setProductAdapter() {
        adapter =
            ProductAdapter(viewModel, this) { productId ->
                ProductDetailActivity.startActivity(this, productId)
            }
        binding.rvProducts.adapter = adapter

        recentProductAdapter = RecentProductAdapter(viewModel, this)
        binding.rvRecentProducts.adapter = recentProductAdapter
    }

    private fun observeProductItems() {
        viewModel.products.observe(this) {
            adapter.setData(it)
        }
    }

    private fun observeRecentProduct() {
        viewModel.recentProducts.observe(this) {
            recentProductAdapter.setData(it)
        }
    }

    private fun setOnLoadMoreButtonClickListener() {
        binding.btnLoadMore.setOnClickListener {
            viewModel.renewCurrentOffset()
            viewModel.loadProducts()
            binding.btnLoadMore.visibility = View.INVISIBLE
        }
    }

    private fun setOnRecyclerViewScrollListener() {
        binding.rvProducts.addOnScrollListener(onScrollListener())
    }

    private fun onScrollListener() =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int,
            ) {
                super.onScrolled(recyclerView, dx, dy)
                binding.btnLoadMore.visibility =
                    if (isLastItemVisible(recyclerView)) View.VISIBLE else View.GONE
            }
        }

    private fun isLastItemVisible(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        return layoutManager.findLastCompletelyVisibleItemPosition() == adapterItemSize()
    }

    private fun adapterItemSize() = adapter.itemCount - OFFSET

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_contents, menu)
        val menuBinding = MenuItemLayoutBinding.inflate(layoutInflater)
        val menuItem = menu?.findItem(R.id.cart_item_count)
        menuItem?.setActionView(menuBinding.root)
        viewModel.totalItemCount.observe(this) {
            menuBinding.menuItemText.text = it.toString()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> CartActivity.startActivity(this)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val OFFSET = 1
    }
}
