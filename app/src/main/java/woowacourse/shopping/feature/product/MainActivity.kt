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
import com.example.domain.CartProduct
import com.example.domain.Product
import com.example.domain.RecentProduct
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
import woowacourse.shopping.R
import woowacourse.shopping.common.adapter.LoadMoreAdapter
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.MockProductRemoteService
import woowacourse.shopping.data.product.MockRemoteProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.product.detail.ProductDetailActivity
import woowacourse.shopping.feature.product.recent.RecentProductListAdapter
import woowacourse.shopping.feature.product.recent.RecentProductListWrapperAdapter
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.RecentProductState
import woowacourse.shopping.model.mapper.toUi
import woowacourse.shopping.util.SpanSizeLookUpManager
import woowacourse.shopping.util.extension.showToast

class MainActivity : AppCompatActivity(), MainContract.View {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val cartRepository: CartRepository by lazy {
        CartRepositoryImpl(
            MockProductRemoteService(),
            CartDao(this)
        )
    }
    private val presenter: MainContract.Presenter by lazy {
        val productRepository: ProductRepository =
            MockRemoteProductRepositoryImpl(MockProductRemoteService())
        val recentProductRepository = RecentProductRepositoryImpl(RecentProductDao(this))
        MainPresenter(this, productRepository, recentProductRepository, cartRepository)
    }
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(
            cartProductStates = cartRepository.getAll().map(CartProduct::toUi),
            onProductClick = presenter::showProductDetail,
            cartProductAddFab = { Thread { presenter.storeCartProduct(it) }.start() },
            cartProductCountMinus = presenter::minusCartProductCount,
            cartProductCountPlus = presenter::plusCartProductCount,
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
        runOnUiThread { presenter.loadMoreProducts() }
    }

    override fun onResume() {
        super.onResume()
        runOnUiThread {
            presenter.loadRecentProducts()
            presenter.loadCartProductCount()
        }
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
        runOnUiThread { productListAdapter.addItems(products) }
    }

    override fun setProducts(products: List<Product>) {
        runOnUiThread { productListAdapter.setItems(products.map(Product::toUi)) }
    }

    override fun setRecentProducts(recentProducts: List<RecentProduct>) {
        runOnUiThread { recentProductListAdapter.setItems(recentProducts.map(RecentProduct::toUi)) }
    }

    override fun setCartProductCount(count: Int) {
        runOnUiThread { cartCountBadge?.text = count.toString() }
    }

    override fun showProductDetail(
        productState: ProductState,
        recentProductState: RecentProductState?
    ) {
        ProductDetailActivity.startActivity(this, productState, recentProductState)
    }

    override fun showEmptyProducts() = showToast("제품이 없습니다.")

    override fun showCartProductCount() {
        runOnUiThread { cartCountBadge?.visibility = VISIBLE }
    }

    override fun hideCartProductCount() {
        runOnUiThread { cartCountBadge?.visibility = GONE }
    }

    private fun initList() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            SpanSizeLookUpManager(concatAdapter, gridLayoutManager.spanCount)

        binding.productRv.layoutManager = gridLayoutManager
        binding.productRv.adapter = concatAdapter
    }
}
