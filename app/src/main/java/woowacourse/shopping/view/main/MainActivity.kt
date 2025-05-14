package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val adapter = ProductsAdapter(viewModel.products, this)
        binding.rvProductList.adapter = adapter
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        binding.rvProductList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    binding.btnLoadMoreProducts.visibility =
                        if (
                            lastVisibleItemPosition == layoutManager.itemCount - 1 &&
                            viewModel.totalSize > viewModel.products.size
                        ) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                }
            },
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.productsLiveData.observe(this) {
            adapter.updateProducts(it)
            binding.rvProductList.adapter?.notifyDataSetChanged()
        }
    }

    override fun onProductSelected(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }
}
