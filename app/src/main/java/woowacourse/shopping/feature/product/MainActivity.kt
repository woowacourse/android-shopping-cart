package woowacourse.shopping.feature.product

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.R
import woowacourse.shopping.common.adapter.LoadMoreAdapter
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.product.detail.ProductDetailActivity
import woowacourse.shopping.feature.product.recent.RecentProductListAdapter
import woowacourse.shopping.feature.product.recent.RecentProductListWrapperAdapter
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.mapper.toUi
import woowacourse.shopping.util.SpanSizeLookUpManager
import woowacourse.shopping.util.extension.showToast

class MainActivity : AppCompatActivity(), MainContract.View {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val presenter: MainContract.Presenter by lazy {
        val productRepository = ProductRepositoryImpl(ProductDao(this))
        val recentProductRepository = RecentProductRepositoryImpl(RecentProductDao(this))
        val cartRepository = CartRepositoryImpl(CartDao(this))
        MainPresenter(this, productRepository, recentProductRepository, cartRepository)
    }
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(
            onProductClick = { presenter.showProductDetail(it) },
            cartProductAddFab = { presenter.storeCartProduct(it) },
            cartProductCountMinus = { presenter.minusCartProductCount(it) },
            cartProductCountPlus = { presenter.plusCartProductCount(it) },
        )
    }
    private val recentProductListAdapter by lazy {
        RecentProductListAdapter(emptyList())
    }
    private val recentProductListWrapperAdapter: RecentProductListWrapperAdapter by lazy {
        RecentProductListWrapperAdapter(recentProductListAdapter)
    }
    private val loadMoreAdapter: LoadMoreAdapter by lazy {
        LoadMoreAdapter(onClick = { presenter.loadMoreProducts() })
    }

    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        ConcatAdapter(config, recentProductListWrapperAdapter, productListAdapter, loadMoreAdapter)
    }

    private var cartCountBadge: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initList()
        presenter.loadMoreProducts()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadRecentProducts()
        presenter.loadCartProductCount()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        cartCountBadge =
            menu?.findItem(R.id.cart_count_badge)?.actionView?.findViewById(R.id.badge)
        presenter.loadCartProductCount()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cart -> CartActivity.startActivity(this)
        }
        return true
    }

    override fun addProductItems(products: List<ProductState>) {
        productListAdapter.addItems(products)
    }

    override fun setProducts(products: List<Product>) {
        productListAdapter.setItems(products.map(Product::toUi))
    }

    override fun setRecentProducts(recentProducts: List<RecentProduct>) {
        recentProductListAdapter.setItems(recentProducts.map(RecentProduct::toUi))
    }

    override fun setCartProductCount(count: Int) {
        cartCountBadge?.text = count.toString()
    }

    override fun showProductDetail(productState: ProductState) {
        ProductDetailActivity.startActivity(this, productState)
    }

    override fun showEmptyProducts() = showToast("제품이 없습니다.")

    override fun showCartProductCount() {
        cartCountBadge?.visibility = VISIBLE
    }

    override fun hideCartProductCount() {
        cartCountBadge?.visibility = GONE
    }

    private fun initList() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            SpanSizeLookUpManager(concatAdapter, gridLayoutManager.spanCount)

        binding.productRv.layoutManager = gridLayoutManager
        binding.productRv.adapter = concatAdapter
    }
}
