package woowacourse.shopping.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.GridWeightLookedUp
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter
import woowacourse.shopping.presentation.ui.home.presenter.HomeContract
import woowacourse.shopping.presentation.ui.home.presenter.Presenter
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity

class HomeActivity : AppCompatActivity(), HomeContract.View {
    private lateinit var binding: ActivityHomeBinding
    override val presenter: HomeContract.Presenter by lazy { initPresenter() }
    private val homeAdapter = HomeAdapter { clickProduct(it) }
    override fun setProducts(products: List<Product>) {
        Log.d("asdf", "products: $products")
        homeAdapter.initProducts(products)
    }

    override fun setRecentlyViewed(products: List<Product>) {
        Log.d("asdf", "recycledViews: $products")
        homeAdapter.initRecentlyViewedProduct(products)
    }

    override fun getProductCount(): Int {
        return homeAdapter.productsCount
    }

    private fun initPresenter(): Presenter {
        return Presenter(
            this,
            ProductRepositoryImpl(
                productDataSource = ProductDao(this),
                recentlyViewedDataSource = RecentlyViewedDao(this),
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMainProducts.adapter = homeAdapter
        initLayoutManager()
        // initProducts(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.getProducts()
        presenter.getRecentlyViewed()
    }

    private fun clickProduct(productId: Long) {
        Log.d("asdf", "productid: $productId")
        startActivity(ProductDetailActivity.getIntent(this, productId))
    }

    private fun initLayoutManager() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = GridWeightLookedUp { homeAdapter.getItemViewType(it) }
        binding.rvMainProducts.layoutManager = gridLayoutManager
    }
}
