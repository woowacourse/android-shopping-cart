package woowacourse.shopping.presentation.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.model.HomeMapper.toProductUiModel
import woowacourse.shopping.presentation.model.HomeMapper.toRecentlyViewedProduct
import woowacourse.shopping.presentation.ui.home.adapter.GridWeightLookedUp
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter
import woowacourse.shopping.presentation.ui.home.adapter.ProductClickListener
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity

class HomeActivity : AppCompatActivity(), HomeContract.View, ProductClickListener {
    private lateinit var binding: ActivityHomeBinding
    override val presenter: HomeContract.Presenter by lazy { initPresenter() }
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(this, ::clickShowMore) }

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
        initAdapter()
        initLayoutManager()
        // 목 데이터 추가 함수 : initProducts(this)
        clickShoppingCart()
        presenter.getProducts()
    }

    override fun onStart() {
        super.onStart()
        presenter.getRecentlyViewed()
    }

    private fun initAdapter() {
        binding.listHomeProducts.apply { adapter = homeAdapter }
    }

    private fun initLayoutManager() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = GridWeightLookedUp { homeAdapter.getItemViewType(it) }
        binding.listHomeProducts.layoutManager = gridLayoutManager
    }

    override fun setProducts(products: List<Product>, isLastProduct: Boolean) {
        homeAdapter.initProducts(products.map { it.toProductUiModel() })
        if (!isLastProduct) homeAdapter.initShowMoreItem()
    }

    override fun setRecentlyViewed(products: List<Product>) {
        homeAdapter.initRecentlyViewedProducts(products.map { it.toRecentlyViewedProduct() })
    }

    private fun clickShoppingCart() {
        binding.imageHomeShoppingCart.setOnClickListener {
            startActivity(ShoppingCartActivity.getIntent(this))
        }
    }

    private fun clickShowMore() {
        presenter.getProducts()
    }

    override fun clickProduct(productId: Long) {
        startActivity(ProductDetailActivity.getIntent(this, productId))
    }
}
