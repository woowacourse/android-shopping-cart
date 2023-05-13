package woowacourse.shopping.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val homeAdapter = HomeAdapter(::setClickEventOnProduct, ::setEventOnShowMoreButton)
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

        initAdapter()

        initLayoutManager()
        // 목 데이터 추가 함수 :
        initProducts(this)
        setClickEventOnShoppingCartButton()
    }

    private fun setEventOnShowMoreButton(productId: Long) {
        presenter.getMoreProducts(productId)
    }

    private fun initAdapter() {
        binding.rvHomeProducts.apply {
            adapter = homeAdapter
            addOnScrollListener(setScrollListener())
        }
    }

    private fun setScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            var scrollState: Int = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (scrollState == 1) {
                    super.onScrolled(recyclerView, dx, dy)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvHomeProducts.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    if (scrollState == 1) return
                    scrollState = 1
                    homeAdapter.initShowMoreButton()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.getProducts()
        presenter.getRecentlyViewed()
    }

    private fun setClickEventOnShoppingCartButton() {
        binding.ivHomeShoppingCart.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
    }

    private fun setClickEventOnProduct(productId: Long) {
        startActivity(ProductDetailActivity.getIntent(this, productId))
    }

    private fun initLayoutManager() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = GridWeightLookedUp { homeAdapter.getItemViewType(it) }
        binding.rvHomeProducts.layoutManager = gridLayoutManager
    }
}
