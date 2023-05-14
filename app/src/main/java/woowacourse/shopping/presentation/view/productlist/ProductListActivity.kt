package woowacourse.shopping.presentation.view.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import woowacourse.shopping.presentation.view.cart.CartActivity
import woowacourse.shopping.presentation.view.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.view.productlist.adpater.MoreProductListAdapter
import woowacourse.shopping.presentation.view.productlist.adpater.ProductListAdapter
import woowacourse.shopping.presentation.view.productlist.adpater.RecentProductListAdapter
import woowacourse.shopping.presentation.view.productlist.adpater.RecentProductWrapperAdapter

class ProductListActivity : AppCompatActivity(), ProductContract.View {
    private lateinit var binding: ActivityProductListBinding

    private val presenter: ProductContract.Presenter by lazy {
        ProductListPresenter(
            this,
            recentProductRepository = RecentProductRepositoryImpl(this)
        )
    }

    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var recentProductListAdapter: RecentProductListAdapter
    private lateinit var recentProductWrapperAdapter: RecentProductWrapperAdapter
    private lateinit var moreProductListAdapter: MoreProductListAdapter

    private val concatAdapter: ConcatAdapter by lazy {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()

        ConcatAdapter(config).apply {
            setConcatAdapter()
        }
    }

    private fun ConcatAdapter.setConcatAdapter() {
        if (recentProductListAdapter.itemCount != 0) {
            addAdapter(recentProductWrapperAdapter)
        }
        addAdapter(productListAdapter)
        addAdapter(moreProductListAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)

        initLayoutManager()
        presenter.deleteNotTodayRecentProducts()
        presenter.loadProductItems()
        presenter.loadRecentProductItems()
        setMoreProductListAdapter()
        setConcatAdapter()
    }

    override fun onRestart() {
        presenter.updateRecentProductItems()
        super.onRestart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                startActivity(CartActivity.createIntent(this))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initLayoutManager() {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    ProductListAdapter.VIEW_TYPE -> 1
                    MoreProductListAdapter.VIEW_TYPE -> 1
                    else -> 2
                }
            }
        }
        binding.rvProductList.layoutManager = layoutManager
    }

    override fun setProductItemsView(products: List<ProductModel>) {
        productListAdapter = ProductListAdapter(products, ::onProductClickEvent)
    }

    override fun setRecentProductItemsView(recentProducts: List<RecentProductModel>) {
        recentProductListAdapter = RecentProductListAdapter(recentProducts, ::moveToActivity)
        recentProductWrapperAdapter = RecentProductWrapperAdapter(recentProductListAdapter)
    }

    private fun setMoreProductListAdapter() {
        moreProductListAdapter = MoreProductListAdapter(productListAdapter, ::onMoreProductList)
    }

    private fun setConcatAdapter() {
        binding.rvProductList.adapter = concatAdapter
    }

    override fun updateRecentProductItemsView(preSize: Int, diffSize: Int) {
        if (!concatAdapter.adapters.contains(recentProductWrapperAdapter)) {
            concatAdapter.addAdapter(0, recentProductWrapperAdapter)
            binding.rvProductList.scrollToPosition(0)
        }
        recentProductListAdapter.notifyItemRangeChanged(preSize, diffSize)
    }

    private fun onProductClickEvent(product: ProductModel) {
        presenter.saveRecentProduct(product.id)
        moveToActivity(product.id)
    }

    private fun moveToActivity(productId: Long) {
        val intent = ProductDetailActivity.createIntent(this, productId)
        startActivity(intent)
    }

    private fun onMoreProductList() {
        presenter.loadMoreData(productListAdapter.itemCount - 1)
    }

    override fun updateMoreProductsView(preSize: Int, diffSize: Int) {
        productListAdapter.notifyItemRangeInserted(preSize, diffSize)
    }
}
