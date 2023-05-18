package woowacourse.shopping.shopping

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.common.model.ShoppingProductModel
import woowacourse.shopping.common.utils.convertDpToPixel
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.data.database.dao.ProductDao
import woowacourse.shopping.data.database.dao.RecentProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shopping.recyclerview.LoadMoreAdapter
import woowacourse.shopping.shopping.recyclerview.ProductAdapter
import woowacourse.shopping.shopping.recyclerview.RecentProductAdapter
import woowacourse.shopping.shopping.recyclerview.RecentProductWrapperAdapter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter
    private var shoppingCartAmount: TextView? = null

    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(emptyList(), onProductItemClick = { presenter.openProduct(it.product) })
    }

    private val recentProductAdapter: RecentProductAdapter by lazy {
        RecentProductAdapter(emptyList())
    }

    private val recentProductWrapperAdapter: RecentProductWrapperAdapter by lazy {
        RecentProductWrapperAdapter(recentProductAdapter)
    }

    private val loadMoreAdapter: LoadMoreAdapter by lazy {
        LoadMoreAdapter {
            presenter.loadMoreProduct()
        }
    }

    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()
        ConcatAdapter(
            config, recentProductWrapperAdapter, productAdapter, loadMoreAdapter
        )
    }

    private val gridItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) - 1
            val column = position % SPAN_COUNT
            val density = resources.displayMetrics.density

            if (position in 0 until SPAN_COUNT) {
                outRect.top += convertDpToPixel(DP_GRID_TOP_OFFSET, density)
            }

            val edgeHorizontalOffset = convertDpToPixel(DP_GRID_EDGE_HORIZONTAL_OFFSET, density)
            if (column == 0) {
                outRect.left += edgeHorizontalOffset
            } else if (column == SPAN_COUNT - 1) {
                outRect.right += edgeHorizontalOffset
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.shopping_toolbar))

        initProductList()

        initPresenter()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateRecentProducts()
        Log.d("test-onresume", findViewById<TextView>(R.id.tv_shopping_cart_amount).text.toString())
    }

    override fun onPause() {
        Log.d("test-onpause", findViewById<TextView>(R.id.tv_shopping_cart_amount).text.toString())
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_shopping, menu)
        val shoppingCartAction = menu?.findItem(R.id.shopping_cart_action)
        shoppingCartAction?.actionView?.setOnClickListener {
            onOptionsItemSelected(shoppingCartAction)
        }

        shoppingCartAmount = shoppingCartAction?.actionView?.findViewById(R.id.tv_shopping_cart_amount)
        presenter.setUpCartAmount()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping_cart_action -> presenter.openCart()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun addProducts(productModels: List<ShoppingProductModel>) {
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

    override fun updateCartAmount(amount: Int) {
        shoppingCartAmount?.text = amount.toString()
    }

    private fun startCartActivity() {
        val intent = CartActivity.createIntent(this)
        startActivity(intent)
    }

    private fun initProductList() {
        binding.shoppingProductList.layoutManager = makeLayoutManager()
        binding.shoppingProductList.addItemDecoration(gridItemDecoration)
        binding.shoppingProductList.adapter = concatAdapter
    }

    private fun initPresenter() {
        val db = ShoppingDBOpenHelper(this).writableDatabase
        presenter = ShoppingPresenter(
            this,
            productRepository = ProductRepositoryImpl(ProductDao(db)),
            recentProductRepository = RecentProductRepositoryImpl(RecentProductDao(db)),
            cartRepository = CartRepositoryImpl(CartDao(db)),
            recentProductSize = 10,
            productLoadSize = 20
        )
    }

    private fun makeLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this, SPAN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (concatAdapter.getItemViewType(position)) {
                        ProductAdapter.VIEW_TYPE -> 1
                        RecentProductWrapperAdapter.VIEW_TYPE -> 2
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

    companion object {
        private const val SPAN_COUNT = 2
        private const val DP_GRID_TOP_OFFSET = 10
        private const val DP_GRID_EDGE_HORIZONTAL_OFFSET = 14
    }
}
