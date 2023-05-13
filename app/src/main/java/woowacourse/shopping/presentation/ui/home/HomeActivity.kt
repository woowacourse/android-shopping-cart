package woowacourse.shopping.presentation.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.common.BindingActivity
import woowacourse.shopping.presentation.ui.home.adapter.GridWeightLookedUp
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter
import woowacourse.shopping.presentation.ui.home.presenter.HomeContract
import woowacourse.shopping.presentation.ui.home.presenter.HomePresenter
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity

class HomeActivity :
    BindingActivity<ActivityHomeBinding>(R.layout.activity_home), HomeContract.View {
    override val presenter: HomeContract.Presenter by lazy { initPresenter() }
    private val homeAdapter = HomeAdapter(::setClickEventOnProduct, ::setEventOnShowMoreButton)

    private fun initPresenter(): HomePresenter {
        return HomePresenter(
            this,
            ProductRepositoryImpl(
                productDataSource = ProductDao(),
                recentlyViewedDataSource = RecentlyViewedDao(),
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 목 데이터 추가 함수 : initProducts(this)

        attachAdapter()
        setUpLayoutManager()
        setClickEventOnShoppingCartButton()
    }

    override fun onStart() {
        super.onStart()
        presenter.fetchProducts()
        presenter.fetchRecentlyViewed()
    }

    private fun attachAdapter() {
        binding.rvHomeProducts.adapter = homeAdapter
    }

    private fun setUpLayoutManager() {
        val gridLayoutManager = initGridLayoutManager()
        binding.rvHomeProducts.layoutManager = gridLayoutManager
    }

    private fun initGridLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this, 2).apply {
            spanSizeLookup = GridWeightLookedUp(homeAdapter::getItemViewType)
        }
    }

    private fun setEventOnShowMoreButton(productId: Long) {
        presenter.fetchMoreProducts(productId)
    }

    private fun setClickEventOnShoppingCartButton() {
        binding.ivHomeShoppingCart.setOnClickListener {
            val intent = ShoppingCartActivity.getIntent(this)
            startActivity(intent)
        }
    }

    private fun setClickEventOnProduct(productId: Long) {
        val intent = ProductDetailActivity.getIntent(this, productId)
        startActivity(intent)
    }

    override fun setUpProducts(products: List<Product>) {
        homeAdapter.initProducts(products)
    }

    override fun setUpRecentlyViewed(products: List<Product>) {
        homeAdapter.initRecentlyViewedProduct(products)
    }

    override fun getProductCount(): Int {
        return homeAdapter.productsCount
    }
}
