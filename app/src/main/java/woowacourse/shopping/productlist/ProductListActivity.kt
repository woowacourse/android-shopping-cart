package woowacourse.shopping.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.util.ViewModelFactory

class ProductListActivity : AppCompatActivity(), ProductListClickAction {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductListAdapter
    private val viewModel: ProductListViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        attachAdapter()
        showProducts()

        binding.btnLoadMoreProducts.setOnClickListener {
            viewModel.loadProducts(adapter.itemCount)
        }

        supportActionBar?.title = "Shopping"
    }

    private fun attachAdapter() {
        adapter = ProductListAdapter(this)
        binding.rcvProductList.adapter = adapter
    }

    private fun showProducts() {
        viewModel.loadProducts(INITIAL_POSITION)
        viewModel.moreProducts.observe(this) { moreProducts ->
            adapter.submitList(moreProducts)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_shopping_cart_nav -> startActivity(ShoppingCartActivity.newInstance(this))
            else -> {}
        }
        return true
    }

    override fun onProductClicked(id: Long) {
        startActivity(ProductDetailActivity.newInstance(this, id))
    }

    companion object {
        private const val INITIAL_POSITION = 0
    }
}
