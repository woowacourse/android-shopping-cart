package woowacourse.shopping.presentation.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.MockProductDao
import woowacourse.shopping.data.product.ProductRemoteDataSource
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.databinding.BadgeCartBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productlist.product.ProductListAdapter
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var activityBinding: ActivityProductListBinding
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private lateinit var cartMenuItem: MenuItem
    private var cartBinding: BadgeCartBinding? = null
    private val productRemoteDataSource: ProductRemoteDataSource by lazy { MockProductDao }
    private val presenter: ProductListPresenter by lazy {
        ProductListPresenter(
            this,
            ProductRepositoryImpl(productRemoteDataSource),
            RecentProductDao(RecentProductDbHelper(this)),
            CartRepositoryImpl(CartDao(CartDbHelper(this)), productRemoteDataSource),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        initView()
    }

    override fun onStart() {
        super.onStart()
        updateView()
    }

    private fun initView() {
        setSupportActionBar(activityBinding.toolbarProductList.toolbar)
        initRecentProductAdapter()
        initProductAdapter()
    }

    private fun updateView() {
        presenter.updateProductItems()
        presenter.updateRecentProductItems()
        if (cartBinding != null) presenter.updateCartCount()
    }

    private fun initProductAdapter() {
        productListAdapter = ProductListAdapter(
            showMoreProductItem = ::showMoreProductItems,
            showProductDetail = ::productClick,
            recentProductAdapter = recentProductAdapter,
            ::showCart,
        )

        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        activityBinding.recyclerProduct.layoutManager = layoutManager.apply {
            spanSizeLookup = ProductListSpanSizeLookup(productListAdapter::getItemViewType)
        }

        activityBinding.recyclerProduct.adapter = productListAdapter
    }

    private fun initRecentProductAdapter() {
        recentProductAdapter = RecentProductAdapter(::productClick)
    }

    override fun loadProductModels(productModels: List<ProductModel>) {
        productListAdapter.setItems(productModels)
    }

    override fun loadRecentProductModels(productModels: List<ProductModel>) {
        recentProductAdapter.submitList(productModels)
    }

    override fun showCartCount(count: Int) {
        cartBinding?.badgeCartCounter?.text = count.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        cartBinding = BadgeCartBinding.inflate(layoutInflater, null, false)
        initCartMenu(menu)
        presenter.updateCartCount()
        return true
    }

    private fun initCartMenu(menu: Menu) {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)
        cartMenuItem = menu.findItem(R.id.icon_cart)
        cartBinding =
            BadgeCartBinding.inflate(
                LayoutInflater.from(this),
                null,
                false,
            )
        cartMenuItem.actionView = cartBinding?.root
        cartBinding?.iconCartMenu?.setOnClickListener {
            startActivity(CartActivity.getIntent(this))
        }
    }

    private fun showMoreProductItems() {
        presenter.updateProductItems()
    }

    private fun productClick(productModel: ProductModel) {
        presenter.saveRecentProduct(productModel.id)
        showProductDetail(productModel)
    }

    private fun showProductDetail(productModel: ProductModel) {
        startActivity(ProductDetailActivity.getIntent(this, productModel))
    }

    private fun showCart() {
        presenter.updateCartCount()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}
