package woowacourse.shopping.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.product.detail.ProductDetailActivity
import woowacourse.shopping.list.adapter.LoadMoreAdapter
import woowacourse.shopping.list.adapter.ProductListAdapter
import woowacourse.shopping.list.adapter.RecentProductListAdapter
import woowacourse.shopping.list.adapter.RecentProductListWrapperAdapter
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.RecentProductState
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
        MainPresenter(this, productRepository, recentProductRepository)
    }
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(onItemClick = { presenter.showProductDetail(it) })
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initList()
        presenter.loadRecentProducts()
        presenter.loadMoreProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
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

    override fun addRecentProductItems(recentProducts: List<RecentProductState>) {
        recentProductListAdapter.addItems(recentProducts)
    }

    override fun setProducts(products: List<Product>) {
        productListAdapter.setItems(products.map(Product::toUi))
    }

    override fun setRecentProducts(recentProducts: List<RecentProduct>) {
        recentProductListAdapter.setItems(recentProducts.map(RecentProduct::toUi))
    }

    override fun showProductDetail(productState: ProductState) {
        ProductDetailActivity.startActivity(this, productState)
    }

    override fun showEmptyProducts() {
        showToast("제품이 없습니다.")
    }

    private fun initList() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            SpanSizeLookUpManager(concatAdapter, gridLayoutManager.spanCount)

        binding.productRv.layoutManager = gridLayoutManager
        binding.productRv.adapter = concatAdapter
    }
}
