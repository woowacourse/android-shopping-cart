package woowacourse.shopping.ui.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
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
import woowacourse.shopping.util.builder.add
import woowacourse.shopping.util.builder.isolatedViewTypeConcatAdapter
import woowacourse.shopping.util.extension.findItemActionView
import woowacourse.shopping.util.extension.findTextView
import woowacourse.shopping.util.extension.getParcelableExtraCompat
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.inject.inject
import woowacourse.shopping.util.listener.ProductClickListener
import woowacourse.shopping.widget.ProductCounterView.OnClickListener

class ShoppingActivity : AppCompatActivity(), View, OnClickListener, ProductClickListener {
    private lateinit var binding: ActivityShoppingBinding
    override val presenter: Presenter by lazy { inject(this, this) }

    private val recentProductAdapter = RecentProductAdapter(presenter::inquiryRecentProductDetail)
    private val recentProductWrapperAdapter = RecentProductWrapperAdapter(recentProductAdapter)
    private val productAdapter = ProductAdapter(this, this)
    private val loadMoreAdapter = LoadMoreAdapter(presenter::loadMore)

    private val basketActivityLauncher = registerForActivityResult(StartActivityForResult()) {
        presenter.refreshProduct()
    }

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
        val basketItemView = binding.shoppingToolBar.findItemActionView(R.id.basket)
        basketItemView?.setOnClickListener { presenter.openBasket() }
    }

    private fun initRecyclerView() {
        binding.adapter = isolatedViewTypeConcatAdapter {
            add(recentProductWrapperAdapter)
            add(productAdapter)
            add(loadMoreAdapter)
        }
    }

    override fun updateProducts(products: List<UiBasketProduct>) {
        productAdapter.submitList(products)
    }

    override fun updateRecentProducts(recentProducts: List<UiRecentProduct>) {
        recentProductWrapperAdapter.submitList(recentProducts)
    }

    override fun navigateToProductDetail(product: UiProduct, recentProduct: UiRecentProduct?) {
        startActivity(ProductDetailActivity.getIntent(this, product, recentProduct))
    }

    override fun navigateToBasket() {
        basketActivityLauncher.launch(BasketActivity.getIntent(this))
    }

    override fun showLoadMoreButton() {
        loadMoreAdapter.showButton()
    }

    override fun hideLoadMoreButton() {
        loadMoreAdapter.hideButton()
    }

    override fun updateBasketProductBadge(count: ProductCount) {
        val basketBadgeView = binding.shoppingToolBar.findItemActionView(R.id.basket) ?: return
        val productCountTextView = basketBadgeView.findTextView(R.id.basket_count_badge) ?: return

        productCountTextView.visibility = count.getVisibility()
        productCountTextView.text = count.toText()
    }

    override fun onClickProduct(product: UiProduct) {
        presenter.inquiryProductDetail(product)
    }

    override fun onClickProductPlus(product: UiProduct) {
        presenter.addBasketProduct(product)
    }

    override fun onClickCounterPlus(product: UiProduct) {
        presenter.addBasketProduct(product)
    }

    override fun onClickCounterMinus(product: UiProduct) {
        presenter.removeBasketProduct(product)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val product = intent?.getParcelableExtraCompat<UiProduct>(PRODUCT_KEY) ?: return
        val count = intent.getIntExtra(COUNT_KEY, 0)
        presenter.addBasketProduct(product, count)
    }

    companion object {
        private const val PRODUCT_KEY = "product_key"
        private const val COUNT_KEY = "count_key"

        fun getIntent(context: Context, product: UiProduct, count: Int): Intent =
            Intent(context, ShoppingActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra(PRODUCT_KEY, product)
                .putExtra(COUNT_KEY, count)
    }
}
