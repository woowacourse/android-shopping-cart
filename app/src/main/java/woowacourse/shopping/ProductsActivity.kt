package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.dummyProducts

class ProductsActivity : BaseActivity<ActivityProductsBinding>(R.layout.activity_products) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onClickHandler =
            object : ProductViewHolder.OnClickHandler {
                override fun onClickProduct(product: Product) {
                    navigateToProductDetail(product)
                }
            }
        binding.rvProducts.adapter = ProductAdapter(dummyProducts, onClickHandler)
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
    }

    private fun navigateToProductDetail(product: Product) {
        val intent =
            Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("PRODUCT", product)
            }
        startActivity(intent)
    }
}
