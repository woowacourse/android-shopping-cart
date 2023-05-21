package woowacourse.shopping.ui.productdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.datasource.basket.local.LocalBasketDataSource
import woowacourse.shopping.data.repository.BasketRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.shopping.ShoppingActivity
import woowacourse.shopping.util.getParcelableExtraCompat
import woowacourse.shopping.util.intentDataNullProcess
import woowacourse.shopping.util.setThrottleFirstOnClickListener

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var currentProduct: UiProduct
    private var previousProduct: UiProduct? = null
    private lateinit var presenter: ProductDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSetResult()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        if (!initExtraData()) return
        initPresenter()
        presenter.initProductData()
        initButtonCloseClickListener()
    }

    private fun initSetResult() {
        setResult(Activity.RESULT_OK, ShoppingActivity.getResultIntent())
    }

    private fun initExtraData(): Boolean {
        currentProduct = intent.getParcelableExtraCompat(CURRENT_PRODUCT_KEY)
            ?: return intentDataNullProcess(CURRENT_PRODUCT_KEY)
        previousProduct = intent.getParcelableExtraCompat(PREVIOUS_PRODUCT_KEY)
        return true
    }

    override fun initBindingData(product: UiProduct) {
        binding.product = product
        binding.addMarketClickListener = presenter::addBasketProduct
    }

    private fun initPresenter() {
        presenter = ProductDetailPresenter(
            this,
            BasketRepositoryImpl(LocalBasketDataSource(BasketDaoImpl(ShoppingDatabase(this)))),
            currentProduct,
            previousProduct
        )
    }

    private fun initButtonCloseClickListener() {
        binding.ivClose.setThrottleFirstOnClickListener {
            finish()
        }
    }

    override fun showBasket() {
        startActivity(BasketActivity.getIntent(this))
        finish()
    }

    companion object {
        private const val CURRENT_PRODUCT_KEY = "currentProduct"
        private const val PREVIOUS_PRODUCT_KEY = "previousProduct"
        fun getIntent(
            context: Context,
            currentProduct: UiProduct,
            previousProduct: UiProduct?
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(CURRENT_PRODUCT_KEY, currentProduct)
                if (previousProduct != null) putExtra(PREVIOUS_PRODUCT_KEY, previousProduct)
            }
    }
}
