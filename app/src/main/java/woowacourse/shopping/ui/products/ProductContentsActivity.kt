package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductContentsBinding
import woowacourse.shopping.model.ProductsImpl
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.ProductDetailActivity

class ProductContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductContentsBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_contents)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_contents)
        adapter =
            ProductAdapter(
                ProductsImpl.findInRange().toMutableList(),
            ) { productId ->
                ProductDetailActivity.startActivity(this, productId)
            }
        setAdapter()
        setOnLoadMoreButtonListener()
        setOnRecyclerViewScrollListener()
    }

    private fun setOnLoadMoreButtonListener() {
        binding.btnLoadMore.setOnClickListener {
            loadMoreProducts()
        }
    }

    private fun setAdapter() {
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = adapter
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

    private fun loadMoreProducts() {
        val newProducts = ProductsImpl.findInRange()
        adapter.addProducts(newProducts)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_contents, menu)
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
