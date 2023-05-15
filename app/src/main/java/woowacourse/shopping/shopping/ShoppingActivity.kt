package woowacourse.shopping.shopping

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.datasource.dao.ProductDao
import woowacourse.shopping.data.datasource.dao.RecentProductDao
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shopping.recyclerview.ProductAdapter
import woowacourse.shopping.shopping.recyclerview.RecentProductAdapter
import woowacourse.shopping.shopping.recyclerview.RecentProductWrapperAdapter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    private val shoppingDBOpenHelper: ShoppingDBOpenHelper by lazy {
        ShoppingDBOpenHelper(this)
    }

    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(emptyList(), onProductItemClick = { presenter.openProduct(it) })
    }

    private val recentProductAdapter: RecentProductAdapter by lazy {
        RecentProductAdapter(emptyList())
    }

    private val recentProductWrapperAdapter: RecentProductWrapperAdapter by lazy {
        RecentProductWrapperAdapter(recentProductAdapter)
    }

    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        ConcatAdapter(
            config, recentProductWrapperAdapter, productAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initToolbar()
        initProductList()
        initLoadMoreButton()
        initPresenter()
    }

    override fun onResume() {
        super.onResume()
        presenter.reloadProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_shopping, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping_cart_action -> presenter.openCart()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateProducts(productModels: List<ProductModel>) {
        productAdapter.updateProducts(productModels)
    }

    override fun addProducts(productModels: List<ProductModel>) {
        productAdapter.addProducts(productModels)
    }

    override fun updateRecentProducts(recentProductModels: List<RecentProductModel>) {
        recentProductAdapter.updateRecentProducts(recentProductModels)
        recentProductWrapperAdapter.updateRecentProduct()
    }

    override fun showProductDetail(productModel: ProductModel) {
        startProductDetailActivity(productModel)
    }

    override fun showCart() {
        startCartActivity()
    }

    private fun initBinding() {
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.shoppingToolbar)
    }

    private fun initLoadMoreButton() {
        binding.loadMoreButton.setOnClickListener {
            presenter.loadMoreProduct()
        }
    }

    private fun initProductList() {
        binding.shoppingProductList.layoutManager = makeLayoutManager()
        binding.shoppingProductList.adapter = concatAdapter
    }

    private fun initPresenter() {
        presenter = ShoppingPresenter(
            this,
            productRepository = ProductRepository(ProductDao(shoppingDBOpenHelper.writableDatabase)),
            recentProductRepository = RecentProductRepository(RecentProductDao(shoppingDBOpenHelper.writableDatabase)),
            recentProductSize = 10,
            productLoadSize = 20
        )
    }

    private fun startCartActivity() {
        val intent = CartActivity.createIntent(this)
        startActivity(intent)
    }

    private fun makeLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (concatAdapter.getWrappedAdapterAndPosition(position).first) {
                        is ProductAdapter -> 1
                        else -> 2
                    }
                }
            }
        }
    }

    private fun startProductDetailActivity(productModel: ProductModel) {
        val intent = ProductDetailActivity.createIntent(this, productModel)
        startActivity(intent)
    }
}
