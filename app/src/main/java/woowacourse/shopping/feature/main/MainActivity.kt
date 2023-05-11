package woowacourse.shopping.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
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
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toItem
import woowacourse.shopping.feature.model.mapper.toUi
import woowacourse.shopping.feature.product.detail.ProductDetailActivity

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

    lateinit var productListadapter: ProductListAdapter
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this, productDbHandler, recentProductDbHandler)

        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initAdapter() {
        // todo 최근 상품이 앱 재시작해야 반영되는 문제 수정 필요 (최근 상품 어댑터의 아이템 업데이트 호출 필요)
        productListadapter = ProductListAdapter(
            onItemClick = { listItem ->
                when (listItem) {
                    is ProductListItem -> {
                        recentProductDbHandler.addColumn(listItem.toUi().toDomain())
                        ProductDetailActivity.startActivity(this@MainActivity, listItem.toUi())
                    }
                }
            }
        )
        binding.productRv.adapter = productListadapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (productListadapter.getItemViewType(position) == ViewType.HORIZONTAL.ordinal) {
                    gridLayoutManager.spanCount
                } else 1
            }
        }
        binding.productRv.layoutManager = gridLayoutManager
    }

    override fun setProducts(products: List<Product>, recentProducts: RecentProducts) {
        productListadapter.setItems(
            products.map { it.toUi().toItem() },
            recentProducts.products.map { it.toUi().toItem() }
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
