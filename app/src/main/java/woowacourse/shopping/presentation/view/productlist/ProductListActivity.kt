package woowacourse.shopping.presentation.view.productlist

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.respository.cart.CartRepositoryImpl
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.databinding.LayoutToolbarCartBinding
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
    private lateinit var toolbarCartBinding: LayoutToolbarCartBinding

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
            presenter.loadCartItems()
        }

    private val cartResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                presenter.loadCartItems()
            }
        }

    private val productListener = object : ProductListener {
        override fun onCountClick(productId: Long, count: Int) {
            Log.d("test", "오냐? count: $count")
            presenter.updateCount(productId, count)
        }

        override fun onItemClick(productId: Long) {
            onProductClickEvent(productId)
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
        if (::recentProductListAdapter.isInitialized) {
            addAdapter(recentProductWrapperAdapter)
        }
        if (::productListAdapter.isInitialized) {
            addAdapter(productListAdapter)
        }
        if (::moreProductListAdapter.isInitialized) {
            addAdapter(moreProductListAdapter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        toolbarCartBinding = LayoutToolbarCartBinding.inflate(layoutInflater)

        initLayoutManager()
        presenter.initRecentProductItems()
        presenter.loadProductItems()
        presenter.loadRecentProductItems()
        presenter.loadCartItems()
        setMoreProductListAdapter()
        setConcatAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product_list_toolbar, menu)

        menu.findItem(R.id.action_cart)?.run {
            actionView = toolbarCartBinding.root
            setToolbarCart()
        }

        return true
    }

    private fun setToolbarCart() {
        toolbarCartBinding.viewToolbarCart.setOnClickListener {
            presenter.actionOptionItem()
        }
    }

    override fun setVisibleToolbarCartCountView() {
        toolbarCartBinding.tvToolbarCartCount.visibility = View.VISIBLE
    }

    override fun setGoneToolbarCartCountView() {
        toolbarCartBinding.tvToolbarCartCount.visibility = View.GONE
    }

    override fun updateToolbarCartCountView(count: Int) {
        toolbarCartBinding.tvToolbarCartCount.text = count.toString()
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
        if (::productListAdapter.isInitialized) {
            productListAdapter.setItems(products)
            return
        }
        productListAdapter = ProductListAdapter(productListener)
        productListAdapter.setItems(products)
        concatAdapter.addAdapter(1, productListAdapter)
    }

    override fun setRecentProductItemsView(recentProducts: List<RecentProductModel>) {
        recentProductListAdapter = RecentProductListAdapter(::onProductClickEvent)
        recentProductListAdapter.setItems(recentProducts)

        recentProductWrapperAdapter = RecentProductWrapperAdapter(
            presenter::getRecentProductsLastScroll,
            presenter::updateRecentProductsLastScroll,
            recentProductListAdapter
        )

        addRecentWrapperAdapter()
    }

    private fun setMoreProductListAdapter() {
        moreProductListAdapter = MoreProductListAdapter(presenter::loadProductItems)
        concatAdapter.addAdapter(moreProductListAdapter)
    }

    private fun setConcatAdapter() {
        binding.rvProductList.adapter = concatAdapter
    }

    override fun updateRecentProductItemsView(recentProducts: List<RecentProductModel>) {
        recentProductListAdapter.setItems(recentProducts)
        addRecentWrapperAdapter()
    }

    private fun addRecentWrapperAdapter() {
        if (!concatAdapter.adapters.contains(recentProductWrapperAdapter)) {
            concatAdapter.addAdapter(RECENT_PRODUCT_ADAPTER_POSITION, recentProductWrapperAdapter)
            binding.rvProductList.scrollToPosition(SCROLL_TOP_POSITION)
            return
        }
    }

    private fun onProductClickEvent(productId: Long) {
        val recentProduct = presenter.getLastRecentProductItem(0)
        presenter.saveRecentProduct(productId)

        val intent = ProductDetailActivity.createIntent(this, productId, recentProduct)
        recentProductResultLauncher.launch(intent)
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
        private const val SPAN_SIZE = 2
        private const val SPAN_SIZE_OF_ONE_COLUMN = 2
        private const val SPAN_SIZE_OF_TWO_COLUMN = 1
        private const val RECENT_PRODUCT_ADAPTER_POSITION = 0
        private const val SCROLL_TOP_POSITION = 0

        private const val KEY_STATE_LAST_SCROLL = "KEY_STATE_LAST_SCROLL"
    }
}
