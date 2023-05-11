package woowacourse.shopping.ui.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
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
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.ShoppingViewHolderType.MORE_BUTTON
import woowacourse.shopping.ui.shopping.ShoppingViewHolderType.PRODUCT
import woowacourse.shopping.ui.shopping.ShoppingViewHolderType.RECENT_PRODUCTS
import woowacourse.shopping.ui.shopping.recentproduct.RecentProductAdapter
import woowacourse.shopping.util.setOnSingleClickListener

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    override lateinit var presenter: ShoppingPresenter
    private lateinit var binding: ActivityShoppingBinding

    private lateinit var shoppingAdapter: ShoppingAdapter
    private lateinit var recentProductAdapter: RecentProductAdapter
    private lateinit var moreButtonAdapter: MoreButtonAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        initPresenter()
        initAdapter()
        initButtonBasketClickListener()
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
    }

    override fun updateProducts(products: List<UiProduct>) {
        shoppingAdapter.submitList(shoppingAdapter.currentList + products)
    }

    override fun updateRecentProducts(recentProducts: List<UiProduct>) {
        shoppingAdapter.updateRecentProduct(recentProducts)
    }

    private fun initAdapter() {
        recentProductAdapter = RecentProductAdapter(presenter::inquiryRecentProduct)
        shoppingAdapter = ShoppingAdapter(recentProductAdapter, presenter::inquiryRecentProduct)
        moreButtonAdapter = MoreButtonAdapter(presenter::fetchProducts)
        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        concatAdapter = ConcatAdapter(config, shoppingAdapter, moreButtonAdapter)

        binding.rvShopping.adapter = concatAdapter
        val gridLayoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (ShoppingViewHolderType.getName(concatAdapter.getItemViewType(position))) {
                        RECENT_PRODUCTS -> 2
                        PRODUCT -> 1
                        MORE_BUTTON -> 2
                    }
            }
        }
        binding.rvShopping.layoutManager = gridLayoutManager
        presenter.fetchRecentProducts()
        presenter.fetchProducts()

        binding.rvShopping.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.fetchHasNext()
                }
            }
        })
    }

    override fun showProductDetail(product: UiProduct) {
        startActivity(ProductDetailActivity.getIntent(this, product))
    }

    override fun updateMoreButtonVisibility(isVisible: Boolean) {
        moreButtonAdapter.updateVisibility(isVisible)
    }

    private fun initButtonBasketClickListener() {
        binding.ivBasket.setOnSingleClickListener {
            startActivity(BasketActivity.getIntent(this))
        }
    }
}
