package woowacourse.shopping.ui.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.ProductListAdapter
import woowacourse.shopping.ui.products.adapter.RecentlyViewedProductListAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {

    private lateinit var binding: ActivityProductListBinding
    private val presenter: ProductListContract.Presenter by lazy {
        ProductListPresenter(
            this,
            RecentlyViewedProductRepositoryImpl(this, ProductRepositoryImpl),
            ProductRepositoryImpl,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProductList()
    }

    override fun onResume() {
        super.onResume()
        initRecentlyViewedProductList()
    }

    private fun initRecentlyViewedProductList() {
        presenter.loadRecentlyViewedProducts()
    }

    private fun initProductList() {
        presenter.loadProducts()
    }

    override fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>) {
        if (recentlyViewedProducts.isEmpty()) {
            binding.layoutRecentlyViewed.isVisible = false
            return
        }

        binding.recyclerViewRecentlyViewed.adapter =
            RecentlyViewedProductListAdapter(recentlyViewedProducts) {
                moveToProductDetailActivity(recentlyViewedProducts[it].id)
            }
    }

    override fun setProducts(products: List<ProductUIState>) {
        binding.recyclerViewMainProduct.adapter = ProductListAdapter(products) {
            presenter.addRecentlyViewedProduct(products[it].id)
            moveToProductDetailActivity(products[it].id)
        }
    }

    private fun moveToProductDetailActivity(productId: Long) {
        ProductDetailActivity.startActivity(this, productId)
    }
}
