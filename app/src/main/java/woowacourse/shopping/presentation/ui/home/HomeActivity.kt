package woowacourse.shopping.presentation.ui.home

import android.content.Intent
import android.os.Bundle
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
import woowacourse.shopping.presentation.ui.home.presenter.HomePresenter
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity
import woowacourse.shopping.util.initProducts

class HomeActivity : AppCompatActivity(), HomeContract.View {
    private lateinit var binding: ActivityHomeBinding
    override val presenter: HomeContract.Presenter by lazy { initPresenter() }
    private val homeAdapter = HomeAdapter(::setClickEventOnProduct)
    override fun setProducts(products: List<Product>) {
        homeAdapter.initProducts(products)
    }

    override fun setRecentlyViewed(products: List<Product>) {
        homeAdapter.initRecentlyViewedProduct(products)
    }

    override fun getProductCount(): Int {
        return homeAdapter.productsCount
    }

    private fun initPresenter(): HomePresenter {
        return HomePresenter(
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
        initProducts(this)
        setClickEventOnShoppingCartButton()
    }

    override fun onStart() {
        super.onStart()
        presenter.getProducts()
        presenter.getRecentlyViewed()
    }

    private fun setClickEventOnShoppingCartButton() {
        binding.ivMainShoppingCart.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
    }

    private fun setClickEventOnProduct(productId: Long) {
        startActivity(ProductDetailActivity.getIntent(this, productId))
    }

    private fun initLayoutManager() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = GridWeightLookedUp { homeAdapter.getItemViewType(it) }
        binding.rvMainProducts.layoutManager = gridLayoutManager
    }
}
