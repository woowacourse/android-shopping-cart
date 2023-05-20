package woowacourse.shopping.presentation.view.productlist

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.local.database.RecentProductDao
import woowacourse.shopping.data.respository.cart.CartRepositoryImpl
import woowacourse.shopping.data.respository.product.ProductRepositoryImpl
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import woowacourse.shopping.presentation.view.cart.CartActivity
import woowacourse.shopping.presentation.view.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.view.productlist.adpater.MoreProductListAdapter
import woowacourse.shopping.presentation.view.productlist.adpater.ProductListAdapter
import woowacourse.shopping.presentation.view.productlist.adpater.RecentProductListAdapter
import woowacourse.shopping.presentation.view.productlist.adpater.RecentProductWrapperAdapter

class ProductListActivity : AppCompatActivity(), ProductContract.View {
    private lateinit var binding: ActivityProductListBinding
    private var cartCountTextView: TextView? = null

    private val presenter: ProductContract.Presenter by lazy {
        ProductListPresenter(
            this,
            productRepository = ProductRepositoryImpl(CartDao(this)),
            recentProductRepository = RecentProductRepositoryImpl(RecentProductDao(this)),
            cartRepository = CartRepositoryImpl(CartDao(this))
        )
    }

    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var recentProductListAdapter: RecentProductListAdapter
    private lateinit var recentProductWrapperAdapter: RecentProductWrapperAdapter
    private lateinit var moreProductListAdapter: MoreProductListAdapter

    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()

        ConcatAdapter(config).apply {
            setConcatAdapter()
        }
    }

    private fun ConcatAdapter.setConcatAdapter() {
        if (recentProductListAdapter.itemCount != 0) {
            addAdapter(recentProductWrapperAdapter)
        }
        addAdapter(productListAdapter)
        addAdapter(moreProductListAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        initLayoutManager()
        presenter.deleteNotTodayRecentProducts()
        presenter.loadProductItems()
        presenter.loadRecentProductItems()
        setMoreProductListAdapter()
        setConcatAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)
        menu?.findItem(R.id.action_cart)?.actionView?.let { view ->
            setMenuView(view)
        }
        observeCartCount()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setMenuView(view: View) {
        view.setOnClickListener {
            startActivity(CartActivity.createIntent(this))
        }
        view.findViewById<TextView>(R.id.tv_cart_badge)?.let {
            cartCountTextView = it
        }
    }

    private fun observeCartCount() {
        presenter.cartCount.observe(
            this
        ) {
            cartCountTextView?.text = it.toString()
        }
    }

    private fun initLayoutManager() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    ProductListAdapter.VIEW_TYPE -> 1
                    MoreProductListAdapter.VIEW_TYPE -> 1
                    else -> 2
                }
            }
        }
        binding.rvProductList.layoutManager = layoutManager
    }

    override fun setProductItemsView(products: List<ProductModel>) {
        productListAdapter =
            ProductListAdapter(products, ::onProductClickEvent, ::onCountChangedEvent)
    }

    override fun setRecentProductItemsView(recentProducts: List<RecentProductModel>) {
        recentProductListAdapter = RecentProductListAdapter(recentProducts, ::moveToActivity)
        recentProductWrapperAdapter = RecentProductWrapperAdapter(recentProductListAdapter)
    }

    private fun setMoreProductListAdapter() {
        moreProductListAdapter = MoreProductListAdapter(::onMoreProductList)
    }

    private fun setConcatAdapter() {
        binding.rvProductList.adapter = concatAdapter
    }

    override fun updateRecentProductItemsView() {
        if (!concatAdapter.adapters.contains(recentProductWrapperAdapter)) {
            concatAdapter.addAdapter(0, recentProductWrapperAdapter)
            binding.rvProductList.scrollToPosition(0)
        }
        recentProductListAdapter.updateDataSet()
    }

    private fun onProductClickEvent(product: ProductModel) {
        presenter.saveRecentProduct(product.id)
        presenter.updateRecentProductItems()
        moveToActivity(product.id)
    }

    private fun onCountChangedEvent(id: Long, Count: Int, position: Int) {
        presenter.updateCartProduct(id, Count, position)
    }

    private fun moveToActivity(productId: Long) {
        val intent = ProductDetailActivity.createIntent(this, productId)
        startActivity(intent)
    }

    private fun onMoreProductList() {
        presenter.loadMoreData(productListAdapter.itemCount - 1)
    }

    override fun updateMoreProductsView(newProducts: List<ProductModel>) {
        productListAdapter.updateDataSet(newProducts)
    }
}
