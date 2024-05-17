package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductContentsBinding
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.ProductDetailActivity

class ProductContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductContentsBinding
    private lateinit var adapter: ProductAdapter
    private val viewModel: ProductContentsViewModel by viewModels {
        ProductContentsViewModelFactory(ProductsImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_contents)

        initBinding()
        setProductAdapter()
        observeProductItems()
        loadItems()
        setOnRecyclerViewScrollListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> CartActivity.startActivity(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_contents)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun setProductAdapter() {
        adapter =
            ProductAdapter { productId ->
                ProductDetailActivity.startActivity(this, productId)
            }
        binding.rvProducts.adapter = adapter
    }

    private fun loadItems() {
        viewModel.loadProducts()
    }

    private fun observeProductItems() {
        viewModel.products.observe(this) {
            adapter.setData(it)
        }
    }

    private fun setOnRecyclerViewScrollListener() {
        binding.rvProducts.addOnScrollListener(
            onScrollListener(),
        )
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

    private fun isLastItemVisible(recyclerView: RecyclerView) =
        (recyclerView.layoutManager as GridLayoutManager)
            .findLastCompletelyVisibleItemPosition() == adapterItemSize()

    private fun adapterItemSize() = adapter.itemCount - OFFSET

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_contents, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private const val OFFSET = 1
    }
}
