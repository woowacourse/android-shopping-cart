package woowacourse.shopping.presentation.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityHomeBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.common.BindingActivity
import woowacourse.shopping.presentation.ui.home.adapter.GridWeightLookedUp
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView
import woowacourse.shopping.presentation.ui.home.presenter.HomeContract
import woowacourse.shopping.presentation.ui.home.presenter.HomePresenter
import woowacourse.shopping.presentation.ui.home.uiModel.Operator.MINUS
import woowacourse.shopping.presentation.ui.home.uiModel.Operator.PLUS
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState
import woowacourse.shopping.presentation.ui.productDetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity

class HomeActivity :
    BindingActivity<ActivityHomeBinding>(R.layout.activity_home),
    HomeContract.View {
    override val presenter: HomeContract.Presenter by lazy { initPresenter() }
    private lateinit var homeAdapter: HomeAdapter

    private fun initPresenter(): HomePresenter {
        return HomePresenter(
            this,
            ProductRepositoryImpl(
                productDataSource = ProductDao(this),
                recentlyViewedDataSource = RecentlyViewedDao(this),
            ),
            ShoppingCartRepositoryImpl(
                shoppingCartDataSource = ShoppingCartDao(this),
                productDataSource = ProductDao(this),
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 목 데이터 추가 함수 :
        // initProducts(this)

        setClickEventOnShoppingCartButton()
    }

    override fun onStart() {
        super.onStart()
        presenter.fetchAllProductsOnHome()
    }

    override fun setUpCountOfProductInCart(productInCart: List<ProductInCartUiState>) {
        homeAdapter.addCountOfProductInCart(productInCart)
    }

    override fun setUpProductsOnHome(
        products: List<ProductsByView>,
        shoppingCart: List<ProductInCartUiState>,
    ) {
        homeAdapter = HomeAdapter(shoppingCart, products, setUpClickListener())
        attachAdapter()
        setUpLayoutManager()
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

    private fun setUpClickListener() = object : SetClickListener {
        override fun setClickEventOnProduct(product: Product) {
            setEventOnProduct(product.id)
        }

        override fun setClickEventOnShowMoreButton() {
            setEventOnShowMoreButton()
        }

        override fun setClickEventOnOperatorButton(operator: Boolean, productInCart: Product) {
            val request = if (operator) PLUS else MINUS
            presenter.addCountOfProductInCart(request, productInCart)
        }
    }

    private fun setEventOnProduct(productId: Long) {
        val intent = ProductDetailActivity.getIntent(this, productId)
        startActivity(intent)
    }

    private fun setEventOnShowMoreButton() {
        presenter.fetchMoreProducts()
    }

    override fun setUpMoreProducts(products: List<ProductsByView>) {
        homeAdapter.addProducts(products)
    }

    private fun setClickEventOnShoppingCartButton() {
        binding.ivHomeShoppingCart.setOnClickListener {
            val intent = ShoppingCartActivity.getIntent(this)
            startActivity(intent)
        }
    }
}
