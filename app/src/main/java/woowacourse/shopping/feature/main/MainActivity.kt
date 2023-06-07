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
import woowacourse.shopping.R
import woowacourse.shopping.data.datasource.cartdatasource.CartLocalDataSourceImpl
import woowacourse.shopping.data.datasource.productdatasource.ProductRemoteDataSourceImpl
import woowacourse.shopping.data.datasource.recentproductdatasource.RecentProductLocalDataSourceImpl
import woowacourse.shopping.data.db.cart.CartDbHelper
import woowacourse.shopping.data.db.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.list.adapter.ProductsAdapter
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.list.item.ProductView.RecentProductsItem
import woowacourse.shopping.feature.product.detail.ProductDetailActivity
import woowacourse.shopping.feature.util.SpanSizeLookUpManager

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productsAdapter: ProductsAdapter
    override lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productDataSource = ProductRemoteDataSourceImpl()
        val productRepository = ProductRepositoryImpl(productDataSource)

        val recentDbHelper = RecentProductDbHelper(this)
        val recentProductSource = RecentProductLocalDataSourceImpl(recentDbHelper)
        val recentProductRepository = RecentProductRepositoryImpl(recentProductSource)

        val cartDbHelper = CartDbHelper(this)
        val cartLocalDataSource = CartLocalDataSourceImpl(cartDbHelper)
        presenter = MainPresenter(this, productRepository, recentProductRepository, cartLocalDataSource)

        initAdapter()
        initListener()
    }

    override fun onResume() {
        presenter.loadInitialData()
        super.onResume()
    }

    private fun initAdapter() {
        productsAdapter = ProductsAdapter(
            onItemClick = { item ->
                when (item) {
                    is CartProductItem -> {
                        presenter.saveRecentProduct(item)
                    }
                    is RecentProductsItem -> Unit
                }
            },
            onAddClick = { item ->
                when (item) {
                    is CartProductItem -> { presenter.addProduct(item) }
                    is RecentProductsItem -> Unit
                }
            },
            onPlusClick = { item ->
                when (item) {
                    is CartProductItem -> { presenter.updateProductCount(item, true) }
                    is RecentProductsItem -> Unit
                }
            },
            onMinusClick = { item ->
                when (item) {
                    is CartProductItem -> { presenter.updateProductCount(item, false) }
                    is RecentProductsItem -> Unit
                }
            },
        )
        binding.productRv.adapter = productsAdapter
        initAdapterLayout()
    }

    override fun startActivity(product: CartProductItem, lastProduct: CartProductItem?) {
        startActivity(ProductDetailActivity.getIntent(this@MainActivity, product, lastProduct))
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
            presenter.loadMoreProducts()
        }
    }

    override fun setInitialProducts(
        products: List<CartProductItem>,
        recentProducts: RecentProductsItem,
    ) {
        val list = listOf(recentProducts) + products
        productsAdapter.setItems(list)
    }

    override fun setProduct(product: CartProductItem) {
        productsAdapter.setItem(product)
    }

    override fun addProducts(products: List<CartProductItem>) {
        productsAdapter.addItems(products)
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
