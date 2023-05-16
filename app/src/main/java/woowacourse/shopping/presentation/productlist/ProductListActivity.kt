package woowacourse.shopping.presentation.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.product.MockProductDao
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.databinding.BadgeCartBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productlist.product.ProductListAdapter
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter
import woowacourse.shopping.repository.RecentProductRepository

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private lateinit var badgeCartCounter: TextView
    private val recentProductRepository: RecentProductRepository by lazy {
        RecentProductDao(RecentProductDbHelper(this))
    }

    private val presenter: ProductListPresenter by lazy {
        ProductListPresenter(this, MockProductDao, recentProductRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onStart() {
        super.onStart()
        presenter.updateRecentProducts()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarProductList.toolbar)
        initRecentProductAdapter()
        initProductAdapter()
    }

    private fun initProductAdapter() {
        productListAdapter = ProductListAdapter(
            showMoreProductItem = ::showMoreProductItems,
            showProductDetail = ::productClick,
            recentProductAdapter = recentProductAdapter,
        )

        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        binding.recyclerProduct.layoutManager = layoutManager.apply {
            spanSizeLookup = ProductListSpanSizeLookup(productListAdapter::getItemViewType)
        }

        binding.recyclerProduct.adapter = productListAdapter
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        initCartCounter(menu)
        return true
    }

    private fun initCartCounter(menu: Menu) {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)
        val cartBinding: BadgeCartBinding = BadgeCartBinding.inflate(layoutInflater, null, false)
        menu.findItem(R.id.icon_cart).actionView = cartBinding.root
        badgeCartCounter = cartBinding.badgeCartCounter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_cart -> startActivity(CartActivity.getIntent(this))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showMoreProductItems() {
        presenter.updateProducts()
    }

    private fun productClick(productModel: ProductModel) {
        presenter.saveRecentProductId(productModel.id)
        showProductDetail(productModel)
    }

    private fun showProductDetail(productModel: ProductModel) {
        startActivity(ProductDetailActivity.getIntent(this, productModel))
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}
