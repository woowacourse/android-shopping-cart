package woowacourse.shopping

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.databinding.ActivityProductsBinding

class ProductsActivity : BaseActivity<ActivityProductsBinding>(R.layout.activity_products) {
    private val viewModel: ProductsViewModel by viewModels()
    private val productAdapter: ProductAdapter = ProductAdapter(createAdapterOnClickHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvProducts.adapter = productAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)

        viewModel.products.observe(this) { products ->
            productAdapter.updateItems(products)
        }

        viewModel.updateProducts(20)
    }

    private fun createAdapterOnClickHandler() =
        object : ProductViewHolder.OnClickHandler {
            override fun onClickProduct(id: Int) {
                navigateToProductDetail(id)
            }
        }

    private fun navigateToProductDetail(id: Int) {
        val intent = ProductDetailActivity.newIntent(this, id)
        startActivity(intent)
    }
}
