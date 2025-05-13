package woowacourse.shopping

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.domain.model.dummyProducts

class ProductsActivity : BaseActivity<ActivityProductsBinding>(R.layout.activity_products) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvProducts.adapter = ProductAdapter(dummyProducts)
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
    }
}
