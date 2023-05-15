package woowacourse.shopping.ui.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.product.ProductDaoImpl
import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDaoImpl
import woowacourse.shopping.data.datasource.product.LocalProductDataSource
import woowacourse.shopping.data.datasource.recentproduct.LocalRecentProductDataSource
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.UiRecentProduct
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.ShoppingViewType.MORE_BUTTON
import woowacourse.shopping.ui.shopping.ShoppingViewType.PRODUCT
import woowacourse.shopping.ui.shopping.ShoppingViewType.RECENT_PRODUCTS
import woowacourse.shopping.ui.shopping.morebutton.MoreButtonAdapter
import woowacourse.shopping.ui.shopping.product.ProductAdapter
import woowacourse.shopping.ui.shopping.recentproduct.RecentProductAdapter
import woowacourse.shopping.ui.shopping.recentproduct.RecentProductWrapperAdapter
import woowacourse.shopping.util.setOnSingleClickListener

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    override lateinit var presenter: ShoppingPresenter
    private lateinit var binding: ActivityShoppingBinding

    private lateinit var recentProductWrapperAdapter: RecentProductWrapperAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var moreButtonAdapter: MoreButtonAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        initPresenter()
        initAdapter()
        initProductData()
        initButtonBasketClickListener()
        initShoppingRecyclerViewScrollListener()
    }

    override fun onResume() {
        super.onResume()
        initRecentProductsData()
    }

    private fun initPresenter() {
        val shoppingDatabase = ShoppingDatabase(this)
        presenter = ShoppingPresenter(
            this,
            ProductRepository(
                LocalProductDataSource(ProductDaoImpl(shoppingDatabase))
            ),
            RecentProductRepository(
                LocalRecentProductDataSource(RecentProductDaoImpl(shoppingDatabase))
            )
        )
        // repeat(100) {
        //     ProductDaoImpl(shoppingDatabase).add(
        //         DataProduct(
        //             0,
        //             "$it",
        //             DataPrice(1000),
        //             "data:image/jpeg;base64,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"
        //         )
        //     )
        // }
    }

    override fun updateProducts(products: List<UiProduct>) {
        productAdapter.submitList(productAdapter.currentList + products)
    }

    override fun updateRecentProducts(recentProducts: List<UiRecentProduct>) {
        recentProductAdapter.submitList(recentProducts)
        recentProductWrapperAdapter.notifyDataSetChanged()
    }

    override fun showProductDetail(product: UiProduct) {
        startActivity(ProductDetailActivity.getIntent(this, product))
    }

    override fun updateMoreButtonState(isVisible: Boolean) {
        moreButtonAdapter.hasNext = isVisible
        moreButtonAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        recentProductAdapter = RecentProductAdapter(presenter::inquiryRecentProductDetail)
        recentProductWrapperAdapter = RecentProductWrapperAdapter(recentProductAdapter)
        productAdapter = ProductAdapter(presenter::inquiryProductDetail)
        moreButtonAdapter = MoreButtonAdapter(presenter::fetchProducts)
        concatAdapter =
            ConcatAdapter(
                getConcatAdapterConfig(),
                recentProductWrapperAdapter,
                productAdapter,
                moreButtonAdapter
            )
        binding.rvShopping.adapter = concatAdapter
        binding.rvShopping.layoutManager = getGridLayoutManager()
    }

    private fun initProductData() {
        presenter.fetchProducts()
    }

    private fun initRecentProductsData() {
        presenter.fetchRecentProducts()
    }

    private fun getConcatAdapterConfig(): ConcatAdapter.Config = ConcatAdapter.Config.Builder()
        .setIsolateViewTypes(false)
        .build()

    private fun getGridLayoutManager(): GridLayoutManager =
        GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (ShoppingViewType.of(concatAdapter.getItemViewType(position))) {
                        RECENT_PRODUCTS -> 2
                        PRODUCT -> 1
                        MORE_BUTTON -> 2
                    }
            }
        }

    private fun initShoppingRecyclerViewScrollListener() {
        binding.rvShopping.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = binding.rvShopping.layoutManager as GridLayoutManager
                val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                if (layoutManager.itemCount <= lastPosition + LOAD_POSITION ||
                    !recyclerView.canScrollVertically(STATE_LOWEST)
                ) {
                    presenter.fetchHasNext()
                }
            }
        })
    }

    private fun initButtonBasketClickListener() {
        binding.ivBasket.setOnSingleClickListener {
            startActivity(BasketActivity.getIntent(this))
        }
    }

    companion object {
        private const val LOAD_POSITION = 4
        private const val STATE_LOWEST = 1
    }
}
