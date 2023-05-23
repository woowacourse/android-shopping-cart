package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.database.DbHelper
import woowacourse.shopping.database.cart.CartItemRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.datasource.cart.CartItemLocalDao
import woowacourse.shopping.datasource.product.ProductMemoryDao
import woowacourse.shopping.datasource.recentlyviewedproduct.RecentlyViewedProductMemoryDao
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.ProductListAdapter
import woowacourse.shopping.ui.products.adapter.RecentlyViewedProductListAdapter
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState
import woowacourse.shopping.utils.customview.CountBadge
import java.lang.IllegalStateException

class ProductListActivity : AppCompatActivity(), ProductListContract.View {

    private val binding: ActivityProductListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }

    private val presenter: ProductListContract.Presenter by lazy {
        ProductListPresenter(
            this,
            RecentlyViewedProductRepositoryImpl(
                RecentlyViewedProductMemoryDao(
                    DbHelper.getDbInstance(this),
                    ProductRepositoryImpl(ProductMemoryDao)
                )
            ),
            ProductRepositoryImpl(ProductMemoryDao),
            CartItemRepositoryImpl(
                CartItemLocalDao(
                    DbHelper.getDbInstance(this),
                    ProductRepositoryImpl(ProductMemoryDao)
                )
            )
        )
    }

    private var cartCountBadge: CountBadge? = null

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
        presenter.onRefreshProducts()
        presenter.onLoadCartItemCount()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)

        cartCountBadge =
            menu.findItem(R.id.cart_count_badge).actionView?.findViewById(R.id.cart_count_badge)
                ?: throw IllegalStateException("장바구니 아이템 개수 배지가 메뉴에 없으면 메뉴 리소스를 다시 보세요.")

        presenter.onLoadCartItemCount()

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
        binding.recyclerViewMainProduct.adapter = ProductListAdapter(
            mutableListOf(),
            { ProductDetailActivity.startActivity(this, it) },
            { presenter.onAddToCart(it) },
            { presenter.onPlusCount(it) },
            { presenter.onMinusCount(it) }
        )
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

    override fun setProducts(products: List<ProductUIState>) {
        binding.recyclerViewMainProduct.adapter = ProductListAdapter(
            products.toMutableList(),
            { ProductDetailActivity.startActivity(this, it) },
            { presenter.onAddToCart(it) },
            { presenter.onPlusCount(it) },
            { presenter.onMinusCount(it) }
        )
    }

    override fun setCanLoadMore(canLoadMore: Boolean) {
        binding.btnLoading.isVisible = canLoadMore
    }

    override fun replaceProduct(product: ProductUIState) {
        (binding.recyclerViewMainProduct.adapter as ProductListAdapter).replaceItem(product)
    }

    override fun setCartItemCount(count: Int) {
        if (count == 0) {
            cartCountBadge?.isVisible = false
            return
        }
        cartCountBadge?.isVisible = true
        cartCountBadge?.count = count
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_PAGE, presenter.getCurrentPage())
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val CURRENT_PAGE = "CURRENT_PAGE"
    }
}
