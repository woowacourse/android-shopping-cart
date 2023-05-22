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
import woowacourse.shopping.data.product.ProductRemoteDataSource
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.ProductService
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
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
    private var cartIconBinding: BadgeCartBinding? = null
    private val productRemoteDataSource: ProductRemoteDataSource by lazy { ProductService }
    private val presenter: ProductListPresenter by lazy {
        ProductListPresenter(
            this,
            ProductRepositoryImpl(productRemoteDataSource),
            RecentProductRepositoryImpl(
                RecentProductDao(RecentProductDbHelper(this)),
                ProductService,
            ),
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
        presenter.updateCartProductInfoList()
    }

    private fun initRecentProductAdapter() {
        recentProductAdapter = RecentProductAdapter(::productClick)
    }

    private fun productClick(productModel: ProductModel) {
        showProductDetail(productModel)
    }

    private fun showProductDetail(productModel: ProductModel) {
        startActivity(ProductDetailActivity.getIntent(this, productModel))
    }

    private fun initProductAdapter() {
        productListAdapter = ProductListAdapter(
            recentProductAdapter = recentProductAdapter,
            presenter = presenter,
        )

        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        activityBinding.recyclerProduct.layoutManager = layoutManager.apply {
            spanSizeLookup = ProductListSpanSizeLookup(productListAdapter::getItemViewType)
        }

        activityBinding.recyclerProduct.adapter = productListAdapter
    }

    override fun loadProductModels(productModels: List<ProductModel>) {
        productListAdapter.setItems(productModels)
    }

    override fun loadRecentProductModels(productModels: List<ProductModel>) {
        recentProductAdapter.submitList(productModels)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        cartIconBinding = BadgeCartBinding.inflate(layoutInflater, null, false)
        initCartIcon(menu)
        return true
    }

    private fun initCartIcon(menu: Menu) {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)
        cartMenuItem = menu.findItem(R.id.icon_cart)
        setUpCartIconBinding()
    }

    private fun setUpCartIconBinding() {
        cartIconBinding =
            BadgeCartBinding.inflate(
                LayoutInflater.from(this),
                null,
                false,
            )
        cartMenuItem.actionView = cartIconBinding?.root
        cartIconBinding?.presenter = presenter
        cartIconBinding?.lifecycleOwner = this
        cartIconBinding?.iconCartMenu?.setOnClickListener {
            startActivity(CartActivity.getIntent(this))
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}
