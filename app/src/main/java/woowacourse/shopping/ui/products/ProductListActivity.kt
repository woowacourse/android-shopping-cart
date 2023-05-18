package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.database.DbHelper
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.ProductListAdapter
import woowacourse.shopping.ui.products.adapter.RecentlyViewedProductListAdapter
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class ProductListActivity : AppCompatActivity(), ProductListContract.View {

    private val binding: ActivityProductListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }

    private val presenter: ProductListContract.Presenter by lazy {
        ProductListPresenter(
            this,
            RecentlyViewedProductRepositoryImpl(DbHelper.getDbInstance(this), ProductRepositoryImpl),
            ProductRepositoryImpl,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()

        initProductList()
        initLoadingButton()
        presenter.onLoadProductsNextPage()
        if (savedInstanceState != null) {
            presenter.restoreCurrentPage(savedInstanceState.getInt(CURRENT_PAGE))
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onLoadRecentlyViewedProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                CartActivity.startActivity(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarProductList)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initProductList() {
        binding.recyclerViewMainProduct.adapter = ProductListAdapter(mutableListOf()) {
            ProductDetailActivity.startActivity(this, it)
        }
    }

    private fun initLoadingButton() {
        binding.btnLoading.setOnClickListener {
            presenter.onLoadProductsNextPage()
        }
    }

    override fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>) {
        if (recentlyViewedProducts.isEmpty()) {
            setUIAboutRecentlyViewedProductIsVisible(false)
            return
        }
        setUIAboutRecentlyViewedProductIsVisible(true)

        binding.recyclerViewRecentlyViewed.adapter =
            RecentlyViewedProductListAdapter(recentlyViewedProducts) {
                ProductDetailActivity.startActivity(this, it)
            }
    }

    private fun setUIAboutRecentlyViewedProductIsVisible(isVisible: Boolean) {
        binding.tvRecentlyViewedProduct.isVisible = isVisible
        binding.recyclerViewRecentlyViewed.isVisible = isVisible
        binding.viewSeparatorRecyclerView.isVisible = isVisible
    }

    override fun addProducts(products: List<ProductUIState>) {
        val adapter = binding.recyclerViewMainProduct.adapter as ProductListAdapter
        adapter.addItems(products)
    }

    override fun setCanLoadMore(canLoadMore: Boolean) {
        binding.btnLoading.isVisible = canLoadMore
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_PAGE, presenter.getCurrentPage())
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val CURRENT_PAGE = "CURRENT_PAGE"
    }
}
