package woowacourse.shopping

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.base.BaseActivity
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Product

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), ProductsEventHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvProductList.adapter = ProductsAdapter(DummyProducts.products, this)
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onProductSelected(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }
}
