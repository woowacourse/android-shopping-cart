package woowacourse.shopping.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.product.ProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductDbHandler
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.extension.showToast
import woowacourse.shopping.feature.list.adapter.ProductListAdapter
import woowacourse.shopping.feature.model.ProductState
import woowacourse.shopping.feature.model.mapper.toItem
import woowacourse.shopping.feature.model.mapper.toUi
import woowacourse.shopping.feature.product.detail.ProductDetailActivity
import woowacourse.shopping.feature.util.SpanSizeLookUpManager

class MainActivity : AppCompatActivity(), MainContract.View {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val presenter: MainContract.Presenter by lazy {
        val productDbHandler = ProductDbHandler(ProductDbHelper(this).writableDatabase)
        val recentProductDbHandler =
            RecentProductDbHandler(RecentProductDbHelper(this).writableDatabase)
        MainPresenter(this, productDbHandler, recentProductDbHandler)
    }
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(
            onItemClick = { presenter.showProductDetail(it) },
            onMoreItemClick = { presenter.loadMoreProducts() }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initList()
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

    override fun addProducts(products: List<Product>) {
        productListAdapter.addItems(products.map { it.toUi().toItem() })
    }

    override fun setProducts(products: List<Product>, recentProducts: List<RecentProduct>) {
        productListAdapter.setItems(
            products.map { it.toUi().toItem() },
            recentProducts.map { it.toUi().toItem() }
        )
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
            SpanSizeLookUpManager(productListAdapter, gridLayoutManager.spanCount)

        binding.productRv.adapter = productListAdapter
        binding.productRv.layoutManager = gridLayoutManager
    }
}
