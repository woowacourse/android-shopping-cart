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
    private lateinit var product: UiProduct
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
        product = intent.getParcelableExtraCompat(PRODUCT_KEY)
            ?: return intentDataNullProcess(PRODUCT_KEY)
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
            product
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
        private const val PRODUCT_KEY = "product"
        fun getIntent(context: Context, product: UiProduct): Intent =
            Intent(context, ProductDetailActivity::class.java).putExtra(PRODUCT_KEY, product)
    }
}
