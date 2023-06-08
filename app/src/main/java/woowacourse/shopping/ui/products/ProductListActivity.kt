package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.listener.ProductItemListener
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.cart.uistate.CartUIState
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
            CartRepositoryImpl(this, ProductRepositoryImpl),
        )
    }
    private var offset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        initLoadingButton()
        initProductList()
    }

    override fun onStart() {
        super.onStart()

        initRecentlyViewedProductList()
        initProductsCartCount()
        initCartItemCount()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                presenter.navigateToCart()
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
        binding.rvMainProduct.adapter = ProductListAdapter(
            mutableListOf<ProductUIState>(),
            object : ProductItemListener {
                override fun onItemClick(productId: Long) {
                    presenter.navigateToProductDetail(productId)
                }

                override fun onPlusCountButtonClick(productId: Long, oldCount: Int) {
                    presenter.plusCount(productId, oldCount)
                }

                override fun onMinusCountButtonClick(productId: Long, oldCount: Int) {
                    presenter.minusCount(productId, oldCount)
                }

                override fun onStartCountButtonClick(product: ProductUIState) {
                    presenter.startCount(product)
                }
            },
        )
        presenter.loadProducts(PAGE_SIZE, offset)
    }

    private fun initLoadingButton() {
        binding.btnLoading.setOnClickListener {
            loadMorePage()
        }
    }

    private fun initProductsCartCount() {
        presenter.loadProductsCartCount()
    }

    private fun initCartItemCount() {
        presenter.loadCartItemCount()
    }

    private fun loadMorePage() {
        offset += PAGE_SIZE
        presenter.loadProducts(PAGE_SIZE, offset)
    }

    override fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>) {
        if (recentlyViewedProducts.isEmpty()) {
            binding.layoutRecentlyViewed.isVisible = false
            return
        }

        binding.layoutRecentlyViewed.isVisible = true
        binding.rvRecentlyViewed.adapter = RecentlyViewedProductListAdapter(recentlyViewedProducts, presenter::navigateToProductDetail)
    }

    override fun addProducts(products: List<ProductUIState>) {
        val adapter = binding.rvMainProduct.adapter as ProductListAdapter
        adapter.addItems(products)
        adapter.notifyItemRangeInserted(adapter.itemCount, products.size)
    }

    override fun updateCartItem(productId: Long, count: Int) {
        val adapter = binding.rvMainProduct.adapter as ProductListAdapter
        adapter.updateCount(productId, count)
    }

    override fun deleteCartItem(productId: Long) {
        val adapter = binding.rvMainProduct.adapter as ProductListAdapter
        adapter.deleteCount(productId)
    }

    override fun updateProductsCartCount(cartProducts: List<CartUIState>) {
        val adapter = binding.rvMainProduct.adapter as ProductListAdapter
        adapter.notifyCountUpdated(cartProducts)
    }

    override fun updateCartItemCount(isVisible: Boolean, itemCount: Int) {
        binding.tvCartCount.isVisible = isVisible
        binding.tvCartCount.text = "$itemCount"
    }

    override fun moveToProductDetailActivity(productId: Long) {
        startActivity(
            ProductDetailActivity.getIntent(this, productId),
        )
    }

    override fun moveToCartActivity() {
        startActivity(
            CartActivity.getIntent(this),
        )
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
