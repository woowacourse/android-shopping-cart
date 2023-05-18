package woowacourse.shopping.presentation.view.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.respository.cart.CartRepositoryImpl
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
import woowacourse.shopping.presentation.view.productlist.adpater.ViewType

class ProductListActivity : AppCompatActivity(), ProductContract.View {
    private lateinit var binding: ActivityProductListBinding

    private val presenter: ProductContract.Presenter by lazy {
        ProductListPresenter(
            this,
            cartRepository = CartRepositoryImpl(this),
            recentProductRepository = RecentProductRepositoryImpl(this)
        )
    }

    private val recentProductResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            presenter.updateRecentProductItems()
        }

    private val cartResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                presenter.loadCartItems()
                productListAdapter.notifyDataSetChanged()
            }
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
        if (recentProductListAdapter.itemCount != EMPTY) {
            addAdapter(recentProductWrapperAdapter)
        }
        addAdapter(productListAdapter)
        addAdapter(moreProductListAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)

        initLayoutManager()
        presenter.initRecentProductItems()
        presenter.loadProductItems()
        presenter.loadRecentProductItems()
        presenter.loadCartItems()
        setMoreProductListAdapter()
        setConcatAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.actionOptionItem(item.itemId)

        return super.onOptionsItemSelected(item)
    }

    private fun initLayoutManager() {
        val layoutManager = GridLayoutManager(this, SPAN_SIZE)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    ViewType.PRODUCT_LIST.ordinal -> SPAN_SIZE_OF_TWO_COLUMN
                    else -> SPAN_SIZE_OF_ONE_COLUMN
                }
            }
        }
        binding.rvProductList.layoutManager = layoutManager
    }

    override fun setProductItemsView(products: List<ProductModel>) {
        productListAdapter = ProductListAdapter(products, presenter::updateProductCount, ::onProductClickEvent)
    }

    override fun setRecentProductItemsView(recentProducts: List<RecentProductModel>) {
        recentProductListAdapter = RecentProductListAdapter(recentProducts) { productId ->
            val intent = ProductDetailActivity.createIntent(this, productId)
            startActivity(intent)
        }
        recentProductWrapperAdapter = RecentProductWrapperAdapter(
            presenter::getRecentProductsLastScroll,
            presenter::updateRecentProductsLastScroll,
            recentProductListAdapter
        )
    }

    private fun setMoreProductListAdapter() {
        moreProductListAdapter = MoreProductListAdapter(::onMoreProductList)
    }

    private fun setConcatAdapter() {
        binding.rvProductList.adapter = concatAdapter
    }

    override fun updateRecentProductItemsView(preSize: Int, diffSize: Int) {
        if (!concatAdapter.adapters.contains(recentProductWrapperAdapter)) {
            concatAdapter.addAdapter(RECENT_PRODUCT_ADAPTER_POSITION, recentProductWrapperAdapter)
            binding.rvProductList.scrollToPosition(SCROLL_TOP_POSITION)
            return
        }
        recentProductListAdapter.updateItemChanged(preSize, diffSize)
    }

    private fun onProductClickEvent(product: ProductModel) {
        presenter.saveRecentProduct(product.id)

        val intent = ProductDetailActivity.createIntent(this, product.id)
        recentProductResultLauncher.launch(intent)
    }

    private fun onMoreProductList() {
        presenter.loadMoreData()
    }

    override fun updateMoreProductsView(preSize: Int, diffSize: Int) {
        productListAdapter.updateItemInserted(preSize, diffSize)
    }

    override fun moveToCartView() {
        cartResultLauncher.launch(CartActivity.createIntent(this))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_STATE_LAST_SCROLL, presenter.getRecentProductsLastScroll())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.updateRecentProductsLastScroll(savedInstanceState.getInt(KEY_STATE_LAST_SCROLL))
    }

    companion object {
        private const val EMPTY = 0
        private const val SPAN_SIZE = 2
        private const val SPAN_SIZE_OF_ONE_COLUMN = 2
        private const val SPAN_SIZE_OF_TWO_COLUMN = 1
        private const val RECENT_PRODUCT_ADAPTER_POSITION = 0
        private const val SCROLL_TOP_POSITION = 0

        private const val KEY_STATE_LAST_SCROLL = "KEY_STATE_LAST_SCROLL"
    }
}
