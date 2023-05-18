package woowacourse.shopping.ui.shopping

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.product.ProductDaoImpl
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductCount
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View
import woowacourse.shopping.ui.shopping.recyclerview.adapter.loadmore.LoadMoreAdapter
import woowacourse.shopping.ui.shopping.recyclerview.adapter.product.ProductAdapter
import woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct.RecentProductAdapter
import woowacourse.shopping.ui.shopping.recyclerview.adapter.recentproduct.RecentProductWrapperAdapter
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.inject.injectShoppingPresenter
import woowacourse.shopping.util.isolatedViewTypeConfig
import woowacourse.shopping.util.listener.ProductClickListener
import woowacourse.shopping.widget.ProductCounterView.OnClickListener

class ShoppingActivity : AppCompatActivity(), View, OnClickListener, ProductClickListener {
    private lateinit var binding: ActivityShoppingBinding
    override val presenter: Presenter by lazy { injectShoppingPresenter(this, this) }

    private val recentProductAdapter = RecentProductAdapter(presenter::inquiryRecentProductDetail)
    private val recentProductWrapperAdapter = RecentProductWrapperAdapter(recentProductAdapter)
    private val productAdapter = ProductAdapter(this, this)
    private val loadMoreAdapter = LoadMoreAdapter(presenter::fetchProducts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater).setContentView(this)
        initView()
    }

    private fun initView() {
        binding.presenter = presenter
        initMenuClickListener()
        initRecyclerView()
    }

    private fun initMenuClickListener() {
        val basketMenuView = binding.shoppingToolBar.menu.findItem(R.id.basket).actionView
        basketMenuView?.setOnClickListener { presenter.openBasket() }
    }

    private fun initRecyclerView() {
        binding.adapter = ConcatAdapter(
            isolatedViewTypeConfig, recentProductWrapperAdapter, productAdapter, loadMoreAdapter
        )
        presenter.fetchAll()
    }

    override fun updateProducts(products: List<UiBasketProduct>) {
        productAdapter.submitList(products)
    }

    override fun updateRecentProducts(recentProducts: List<UiRecentProduct>) {
        recentProductWrapperAdapter.submitList(recentProducts)
    }

    override fun showProductDetail(product: UiProduct, recentProduct: UiRecentProduct?) {
        startActivity(ProductDetailActivity.getIntent(this, product, recentProduct))
    }

    override fun navigateToBasketScreen() {
        startActivity(BasketActivity.getIntent(this))
    }

    override fun showLoadMoreButton() {
        loadMoreAdapter.showButton()
    }

    override fun hideLoadMoreButton() {
        loadMoreAdapter.hideButton()
    }

    override fun updateBasketProductCount(count: ProductCount) {
        val basketBadgeView = binding.shoppingToolBar.menu.findItem(R.id.basket).actionView
        val countBadge = basketBadgeView?.findViewById<TextView>(R.id.basket_count_badge)
        if (count.value == 0) countBadge?.visibility = GONE else countBadge?.visibility = VISIBLE
        countBadge?.text = count.toText()
    }

    override fun onProductClick(product: UiProduct) {
        presenter.inquiryProductDetail(product)
    }

    override fun onPlusProductClick(product: UiProduct) {
        presenter.addBasketProduct(product)
    }

    override fun onClickPlus(product: UiProduct) {
        presenter.addBasketProduct(product)
    }

    override fun onClickMinus(product: UiProduct) {
        presenter.removeBasketProduct(product)
    }

    companion object {
        fun insertDummies(context: Context, size: Int) {
            (0 until size).forEach { id ->
                ProductDaoImpl(ShoppingDatabase(context)).add(
                    DataProduct(
                        id,
                        "name $id",
                        DataPrice(1000),
                        "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001939]_20210225094313315.jpg"
                    )
                )
            }
        }
    }
}
