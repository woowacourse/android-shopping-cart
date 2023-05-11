package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import woowacourse.shopping.R
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

    private lateinit var binding: ActivityProductListBinding
    private val presenter: ProductListContract.Presenter by lazy {
        ProductListPresenter(
            this,
            RecentlyViewedProductRepositoryImpl(this, ProductRepositoryImpl),
            ProductRepositoryImpl,
        )
    }
    private var offset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        initProductList()
        initLoadingButton()
    }

    override fun onResume() {
        super.onResume()
        initRecentlyViewedProductList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                moveToCartActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarProductList)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initRecentlyViewedProductList() {
        presenter.loadRecentlyViewedProducts()
    }

    private fun initProductList() {
        binding.recyclerViewMainProduct.adapter = ProductListAdapter(mutableListOf()) {
            presenter.addRecentlyViewedProduct(it)
            moveToProductDetailActivity(it)
        }
        presenter.loadProducts(PAGE_SIZE, offset)
        offset += PAGE_SIZE
    }

    private fun initLoadingButton() {
        binding.btnLoading.setOnClickListener {
            presenter.loadProducts(PAGE_SIZE, offset)
            offset += PAGE_SIZE
        }
    }

    override fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>) {
        if (recentlyViewedProducts.isEmpty()) {
            binding.layoutRecentlyViewed.isVisible = false
            return
        }

        binding.recyclerViewRecentlyViewed.adapter =
            RecentlyViewedProductListAdapter(recentlyViewedProducts) {
                moveToProductDetailActivity(recentlyViewedProducts[it].id)
            }
    }

    override fun addProducts(products: List<ProductUIState>) {
        val adapter = binding.recyclerViewMainProduct.adapter as ProductListAdapter
        adapter.addItems(products)
        adapter.notifyDataSetChanged()
    }

    private fun moveToProductDetailActivity(productId: Long) {
        ProductDetailActivity.startActivity(this, productId)
    }

    private fun moveToCartActivity() {
        CartActivity.startActivity(this)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
