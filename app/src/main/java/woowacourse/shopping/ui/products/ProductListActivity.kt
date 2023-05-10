package woowacourse.shopping.ui.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.ui.products.adapter.ProductListAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {

    private lateinit var binding: ActivityProductListBinding
    private val presenter: ProductListContract.Presenter by lazy {
        ProductListPresenter(this, ProductRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProductList()
    }

    private fun initProductList() {
        presenter.loadProducts()
    }

    override fun setProducts(products: List<ProductUIState>) {
        binding.recyclerViewMainProduct.adapter = ProductListAdapter(products) {
            moveToProductDetailActivity(products[it])
        }
    }

    private fun moveToProductDetailActivity(product: ProductUIState) {
    }
}
