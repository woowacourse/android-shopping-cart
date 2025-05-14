package woowacourse.shopping.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.page.Page
import kotlin.getValue

class MainActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    ProductsEventHandler {
    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.handler = this
        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.rvProductList.adapter = ProductsAdapter(this)
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        binding.rvProductList.addOnScrollListener(ProductsOnScrollListener(binding, viewModel))
        viewModel.requestProductsPage(0)
        viewModel.productsLiveData.observe(this) { page -> updateRecyclerView(page) }
    }

    private fun updateRecyclerView(page: Page<Product>) {
        (binding.rvProductList.adapter as ProductsAdapter).updateProducts(page.items)
        binding.rvProductList.adapter?.notifyDataSetChanged()
    }

    override fun onProductSelected(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onLoadMoreProducts(page: Int) {
        viewModel.requestProductsPage(page)
    }
}
