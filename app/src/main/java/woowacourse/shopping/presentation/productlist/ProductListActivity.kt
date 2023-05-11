package woowacourse.shopping.presentation.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.product.MockProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductIdDbAdapter
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.cart.CartActivity
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.productlist.product.ProductListAdapter
import woowacourse.shopping.presentation.productlist.product.ProductListAdapter.Companion.RECENT_PRODUCT_VIEW_POSITION
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var binding: ActivityProductListBinding

    private lateinit var recentProductAdapter: RecentProductAdapter

    private val recentProductRepository: RecentProductIdRepository by lazy {
        RecentProductIdDbAdapter(RecentProductDbHelper(this))
    }

    private val presenter: ProductListPresenter by lazy {
        ProductListPresenter(this, MockProductRepository, recentProductRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initRecyclerView()
        setSupportActionBar(binding.toolbarProductList.toolbar)
    }

    private fun initRecyclerView() {
        setLayoutManager()
        presenter.initRecentProducts()
        presenter.initProducts()
    }

    override fun onStart() {
        super.onStart()
        presenter.updateRecentProducts()
    }

    override fun setRecentProductModels(productModels: List<ProductModel>) {
        recentProductAdapter.setItems(productModels)
    }

    override fun initProductModels(productModels: List<ProductModel>) {
        binding.recyclerProduct.adapter = ProductListAdapter(
            productModels,
            ::productClick,
            recentProductAdapter,
        )
    }

    override fun initRecentProductModels(productModels: List<ProductModel>) {
        recentProductAdapter = RecentProductAdapter(productModels, ::productClick)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_cart -> startActivity(CartActivity.getIntent(this))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun productClick(productModel: ProductModel) {
        presenter.saveRecentProductId(productModel.id)
        showProductDetail(productModel)
    }

    private fun showProductDetail(productModel: ProductModel) {
        startActivity(ProductDetailActivity.getIntent(this, productModel))
    }

    private fun setLayoutManager() {
        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == RECENT_PRODUCT_VIEW_POSITION) return RECENT_PRODUCT_SPAN_SIZE
                return PRODUCT_SPAN_SIZE
            }
        }
        binding.recyclerProduct.layoutManager = layoutManager
    }

    companion object {
        private const val RECENT_PRODUCT_SPAN_SIZE = 2
        private const val PRODUCT_SPAN_SIZE = 1
        private const val SPAN_COUNT = 2
    }
}
