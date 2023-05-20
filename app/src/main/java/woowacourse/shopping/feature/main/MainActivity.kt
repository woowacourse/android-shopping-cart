package woowacourse.shopping.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.example.domain.Product
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.product.ProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductDbHandler
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.list.adapter.ProductsAdapter
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.model.mapper.toItem
import woowacourse.shopping.feature.model.mapper.toUi
import woowacourse.shopping.feature.product.detail.ProductDetailActivity
import woowacourse.shopping.feature.util.SpanSizeLookUpManager

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding

    lateinit var productsAdapter: ProductsAdapter
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productDb = ProductDbHandler(ProductDbHelper(this).writableDatabase)
        val recentDb = RecentProductDbHandler(RecentProductDbHelper(this).writableDatabase)
        presenter = MainPresenter(this, productDb, recentDb)

        initAdapter()
        initListener()

        presenter.addProducts()
    }

    private fun initAdapter() {
        productsAdapter = ProductsAdapter(
            onItemClick = { listItem ->
                when (listItem) {
                    is ProductView.ProductItem -> {
                        presenter.storeRecentProduct(listItem)
                        ProductDetailActivity.startActivity(this@MainActivity, listItem.toUi())
                    }
                    is ProductView.RecentProductsItem -> Unit
                }
            },
        )
        binding.productRv.adapter = productsAdapter
        initAdapterLayout()
    }

    private fun initAdapterLayout() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            SpanSizeLookUpManager(productsAdapter, gridLayoutManager.spanCount)
        binding.productRv.layoutManager = gridLayoutManager
    }

    private fun initListener() {
        binding.productRv.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(DIRECTION_DOWN) && dy > 0) {
                    binding.mainMoreButton.visibility = VISIBLE
                }
            }
        })

        binding.mainMoreButton.setOnClickListener {
            binding.mainMoreButton.visibility = GONE
            presenter.addProducts()
        }
    }

    override fun addProducts(products: List<Product>) {
        productsAdapter.addItems(
            products.map { it.toUi().toItem() },
        )
    }

    override fun setProducts(products: List<ProductView.ProductItem>, recentProducts: ProductView.RecentProductsItem) {
        val list = listOf(recentProducts) + products
        productsAdapter.setItems(list)
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

    companion object {
        private const val DIRECTION_DOWN = 1
    }
}
