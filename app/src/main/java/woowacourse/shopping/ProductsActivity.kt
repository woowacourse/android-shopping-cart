package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.domain.model.Product

class ProductsActivity : BaseActivity<ActivityProductsBinding>(R.layout.activity_products) {
    private val viewModel: ProductsViewModel by viewModels()
    private val productAdapter: ProductAdapter = ProductAdapter(onClickHandler = createAdapterOnClickHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvProducts.adapter = productAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        viewModel.updateProducts(20)
        viewModel.products.observe(this) { products ->
            productAdapter.updateItems(products)
        }
    }

    private fun createAdapterOnClickHandler() =
        object : ProductViewHolder.OnClickHandler {
            override fun onClickProduct(product: Product) {
                navigateToProductDetail(product)
            }
        }

    private fun navigateToProductDetail(product: Product) {
        val intent =
            Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("PRODUCT", product)
            }
        startActivity(intent)
    }
}
