package woowacourse.shopping.view.inventory

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.page.Page
import kotlin.getValue

class InventoryActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    InventoryEventHandler {
    private val viewModel: InventoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar as Toolbar)
        binding.apply {
            viewModel = this@InventoryActivity.viewModel
            handler = this@InventoryActivity
        }
        initRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    private fun initRecyclerview() {
        binding.rvProductList.apply {
            adapter = ProductsAdapter(this@InventoryActivity)
            layoutManager = GridLayoutManager(this@InventoryActivity, 2)
            addOnScrollListener(InventoryOnScrollListener(binding, viewModel))
        }
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@InventoryActivity) { page -> updateRecyclerView(page) }
        }
    }

    private fun updateRecyclerView(page: Page<Product>) {
        binding.rvProductList.adapter.apply {
            (this as ProductsAdapter).updateProducts(page.items)
            notifyItemInserted(itemCount)
        }
    }

    override fun onProductSelected(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onLoadMoreProducts(page: Int) {
        binding.btnLoadMoreProducts.visibility = View.GONE
        viewModel.requestProductsPage(page)
    }
}
