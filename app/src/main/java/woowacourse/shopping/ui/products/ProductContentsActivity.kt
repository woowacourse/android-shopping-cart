package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductContentsBinding
import woowacourse.shopping.model.ProductsImpl
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.ProductDetailActivity

class ProductContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductContentsBinding
    private lateinit var adapter: ProductAdapter

    private var currentOffset = 0
    private val loadLimit = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_contents)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_contents)
        adapter =
            ProductAdapter(
                ProductsImpl.findAll(currentOffset, loadLimit).toMutableList(),
            ) { productId ->
                ProductDetailActivity.startActivity(this, productId)
            }
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = adapter

        binding.btnLoadMore.setOnClickListener {
            loadMoreProducts()
        }
    }

    private fun loadMoreProducts() {
        val newProducts = ProductsImpl.findAll(currentOffset, loadLimit)
        adapter.addProducts(newProducts)
        currentOffset += loadLimit
        binding.btnLoadMore.visibility =
            if (newProducts.size == loadLimit) View.VISIBLE else View.GONE
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
}
