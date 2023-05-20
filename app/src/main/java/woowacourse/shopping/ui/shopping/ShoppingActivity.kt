package woowacourse.shopping.ui.shopping

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.RecentProductUIModel
import woowacourse.shopping.repositoryImpl.MockWeb
import woowacourse.shopping.repositoryImpl.RemoteProductRepository
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detailedProduct.DetailedProductActivity
import woowacourse.shopping.ui.shopping.productAdapter.ProductsAdapter
import woowacourse.shopping.ui.shopping.productAdapter.ProductsAdapterDecoration.getItemDecoration
import woowacourse.shopping.ui.shopping.productAdapter.ProductsAdapterDecoration.getSpanSizeLookup
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    private lateinit var mockWeb: MockWeb

    private val adapter: ProductsAdapter = ProductsAdapter(getAdapterListener())
    private var tvCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMockWeb()
        initBinding()
        initToolbar()
        initPresenter()
        initLayoutManager()
    }

    override fun onResume() {
        super.onResume()
        presenter.setUpRecentProducts()
        presenter.setUpCartCounts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        menu?.findItem(R.id.cart)?.actionView?.let { view ->
            view.setOnClickListener { navigateToCart() }
            view.findViewById<TextView>(R.id.tv_counter)?.let { tvCount = it }
        }
        presenter.setUpTotalCount()
        return true
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        binding.rvProducts.adapter = adapter
        binding.rvProducts.itemAnimator = null
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initMockWeb() {
        val thread = Thread { mockWeb = MockWeb() }
        thread.start()
        thread.join()
    }

    private fun initPresenter() {
        presenter = ShoppingPresenter(
            this,
            RemoteProductRepository(mockWeb.url),
            RecentProductDatabase(this),
            CartDatabase(this)
        )
        presenter.setUpRecentProducts()
        presenter.setUpNextProducts()
        presenter.setUpCartCounts()
    }

    private fun initLayoutManager() {
        val layoutManager = binding.rvProducts.layoutManager as GridLayoutManager
        layoutManager.spanSizeLookup = getSpanSizeLookup(layoutManager, adapter)
        binding.rvProducts.addItemDecoration(getItemDecoration(layoutManager, resources))
    }

    private fun getAdapterListener() = object : ProductsListener {
        override fun onClickItem(productId: Int) {
            presenter.navigateToItemDetail(productId)
        }
        override fun onReadMoreClick() {
            presenter.setUpNextProducts()
        }
        override fun onAddCartOrUpdateCount(productId: Int, count: Int) {
            adapter.updateItemCount(productId, count)
            presenter.updateItemCount(productId, count)
            presenter.setUpTotalCount()
        }
    }

    override fun addMoreProducts(products: List<ProductUIModel>) {
        adapter.addList(products)
    }

    override fun setRecentProducts(recentProductsData: List<RecentProductUIModel>) {
        adapter.updateRecentProducts(recentProductsData)
    }

    override fun setCartProducts(cartCounts: Map<Int, Int>) {
        adapter.updateCartCounts(cartCounts)
    }

    override fun navigateToProductDetail(product: ProductUIModel) {
        startActivity(DetailedProductActivity.getIntent(this, product))
    }

    override fun setToolbar(totalCount: Int) {
        tvCount?.text = totalCount.toString()
    }

    private fun navigateToCart() {
        startActivity(CartActivity.getIntent(this))
    }
}
