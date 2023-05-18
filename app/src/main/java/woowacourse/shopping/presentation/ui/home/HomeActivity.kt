package woowacourse.shopping.presentation.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.presentation.model.HomeData
import woowacourse.shopping.presentation.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.ui.home.adapter.GridWeightLookedUp
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter
import woowacourse.shopping.presentation.ui.home.adapter.ProductClickListener
import woowacourse.shopping.presentation.ui.home.adapter.RecentlyViewedProductAdapter
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity

class HomeActivity : AppCompatActivity(), HomeContract.View, ProductClickListener {
    private lateinit var binding: ActivityHomeBinding
    override val presenter: HomeContract.Presenter by lazy { initPresenter() }
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(recentlyViewedProductAdapter, this, ::clickShowMore)
    }
    private val recentlyViewedProductAdapter: RecentlyViewedProductAdapter by lazy {
        RecentlyViewedProductAdapter(this)
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
        initAdapter()
        initLayoutManager()
        // 목 데이터 추가 함수 : initProducts(this)
        clickShoppingCart()
        presenter.setHome()
        presenter.fetchProducts()
    }

    override fun onStart() {
        super.onStart()
        presenter.fetchRecentlyViewed()
    }

    private fun initAdapter() {
        binding.listHomeProducts.apply { adapter = homeAdapter }
    }

    private fun initLayoutManager() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = GridWeightLookedUp { homeAdapter.getItemViewType(it) }
        binding.listHomeProducts.layoutManager = gridLayoutManager
    }

    override fun setHomeData(homeData: List<HomeData>) {
        homeAdapter.submitList(homeData)
    }

    override fun initRecentlyViewed() {
        homeAdapter.notifyItemInserted(0)
    }

    override fun updateRecentlyViewedProducts(products: List<RecentlyViewedProduct>) {
        recentlyViewedProductAdapter.submitList(products)
    }

    override fun appendProductItems(startPosition: Int, size: Int) {
        homeAdapter.notifyItemRangeInserted(startPosition, size)
    }

    override fun appendShowMoreItem(position: Int) {
        homeAdapter.notifyItemInserted(position)
    }

    override fun removeShowMoreItem(position: Int) {
        homeAdapter.notifyItemRemoved(position)
    }

    private fun clickShoppingCart() {
        binding.imageHomeShoppingCart.setOnClickListener {
            startActivity(ShoppingCartActivity.getIntent(this))
        }
    }

    override fun clickProduct(productId: Long) {
        startActivity(ProductDetailActivity.getIntent(this, productId))
    }

    private fun clickShowMore() {
        presenter.fetchProducts()
    }
}
