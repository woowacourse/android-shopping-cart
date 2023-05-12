package woowacourse.shopping.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.Product
import com.example.domain.RecentProducts
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.product.ProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductDbHandler
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.list.adapter.ProductListAdapter
import woowacourse.shopping.feature.list.item.ProductListItem
import woowacourse.shopping.feature.model.mapper.toItem
import woowacourse.shopping.feature.model.mapper.toUi
import woowacourse.shopping.feature.product.detail.ProductDetailActivity
import woowacourse.shopping.feature.util.SpanSizeLookUpManager

class MainActivity : AppCompatActivity(), MainContract.View {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!
    private val productDbHandler: ProductDbHandler by lazy {
        ProductDbHandler(ProductDbHelper(this).writableDatabase)
    }
    private val recentProductDbHandler: RecentProductDbHandler by lazy {
        RecentProductDbHandler(RecentProductDbHelper(this).writableDatabase)
    }

    lateinit var productListAdapter: ProductListAdapter
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this, productDbHandler, recentProductDbHandler)
        initAdapter()
        presenter.addProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initAdapter() {
        productListAdapter = ProductListAdapter(
            onItemClick = { listItem ->
                when (listItem) {
                    is ProductListItem -> {
                        presenter.storeRecentProduct(listItem)
                        ProductDetailActivity.startActivity(this@MainActivity, listItem.toUi())
                    }
                }
            },
            onMoreItemClick = {
                presenter.addProducts()
            },
        )
        binding.productRv.adapter = productListAdapter
        initLayout()
    }

    private fun initLayout() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            SpanSizeLookUpManager(productListAdapter, gridLayoutManager.spanCount)
        binding.productRv.layoutManager = gridLayoutManager
    }

    override fun addProducts(products: List<Product>) {
        productListAdapter.addItems(
            products.map { it.toUi().toItem() },
        )
    }

    override fun setProducts(products: List<Product>, recentProducts: RecentProducts) {
        productListAdapter.setItems(
            products.map { it.toUi().toItem() },
            recentProducts.products.map { it.toUi().toItem() },
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cart -> {
                CartActivity.startActivity(this)
            }
        }
        return true
    }
}
