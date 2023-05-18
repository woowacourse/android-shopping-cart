package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.productdetail.ProductDetailContract.Presenter
import woowacourse.shopping.ui.productdetail.ProductDetailContract.View
import woowacourse.shopping.util.extension.getParcelableExtraCompat
import woowacourse.shopping.util.extension.setContentView
import woowacourse.shopping.util.extension.showImage
import woowacourse.shopping.util.inject.injectProductDetailPresenter

class ProductDetailActivity : AppCompatActivity(), View, OnMenuItemClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    override val presenter: Presenter by lazy {
        injectProductDetailPresenter(
            view = this,
            context = this,
            detailProduct = intent.getParcelableExtraCompat(DETAIL_PRODUCT_KEY)!!,
            recentProduct = intent.getParcelableExtraCompat(LAST_VIEWED_PRODUCT_KEY),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater).setContentView(this)
        initView()
    }

    private fun initView() {
        binding.presenter = presenter
        binding.productDetailToolBar.setOnMenuItemClickListener(this)
    }

    override fun showProductDetail(product: UiProduct) {
        binding.detailProduct = product
    }

    override fun showLastViewedProductDetail(product: UiProduct?) {
        binding.lastViewedProduct = product
    }

    override fun navigateToBasketScreen() {
        startActivity(BasketActivity.getIntent(this))
    }

    override fun navigateToProductDetail(recentProduct: UiRecentProduct) {
        startActivity(getIntent(this, recentProduct.product, null))
        finish()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close -> finish()
        }
        return true
    }

    companion object {
        private const val DETAIL_PRODUCT_KEY = "detail_product_key"
        private const val LAST_VIEWED_PRODUCT_KEY = "last_viewed_product_key"

        fun getIntent(context: Context, detail: UiProduct, recent: UiRecentProduct?): Intent =
            Intent(context, ProductDetailActivity::class.java)
                .putExtra(DETAIL_PRODUCT_KEY, detail)
                .putExtra(LAST_VIEWED_PRODUCT_KEY, recent)
    }
}
