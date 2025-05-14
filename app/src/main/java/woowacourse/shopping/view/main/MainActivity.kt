package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.detail.ProductDetailActivity

class MainActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    ProductsEventHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvProductList.adapter = ProductsAdapter(DummyProducts.products, this)
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
                        if (lastVisibleItemPosition == layoutManager.itemCount - 1) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                }
            },
        )
    }

    override fun onProductSelected(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }
}
