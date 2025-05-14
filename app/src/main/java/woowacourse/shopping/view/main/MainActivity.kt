package woowacourse.shopping.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.detail.ProductDetailActivity
import kotlin.getValue

class MainActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    ProductsEventHandler {
    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.handler = this
        initRecyclerview()
    }

    private fun initRecyclerview() {
        val adapter = ProductsAdapter( this)
        viewModel.requestProductsPage(1)
        binding.rvProductList.adapter = adapter
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        binding.rvProductList.addOnScrollListener(ProductsOnScrollListener(binding, viewModel))
        viewModel.productsLiveData.observe(this) {
            adapter.updateProducts(it)
            binding.rvProductList.adapter?.notifyDataSetChanged()
        }
    }

    override fun onProductSelected(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onLoadMoreProducts(page: Int) {
        viewModel.requestProductsPage(page)
    }
}
