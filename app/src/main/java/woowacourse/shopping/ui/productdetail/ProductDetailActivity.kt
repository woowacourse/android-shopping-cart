package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.dao.basket.BasketDaoImpl
import woowacourse.shopping.data.datasource.basket.local.LocalBasketDataSource
import woowacourse.shopping.data.repository.BasketRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.util.getParcelableExtraCompat
import woowacourse.shopping.util.intentDataNullProcess
import woowacourse.shopping.util.setOnSingleClickListener

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: UiProduct
    override lateinit var presenter: ProductDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        initExtraData()
        initPresenter()
        initBindingData()
        initButtonCloseClickListener()
    }

    private fun initExtraData() {
        product = intent.getParcelableExtraCompat(PRODUCT_KEY)
            ?: return intentDataNullProcess(PRODUCT_KEY)
    }

    private fun initBindingData() {
        binding.product = product
        binding.productDetailPresenter = presenter
    }

    private fun initPresenter() {
        presenter = ProductDetailPresenter(
            this,
            BasketRepository(LocalBasketDataSource(BasketDaoImpl(ShoppingDatabase(this)))),
            product
        )
    }

    private fun initButtonCloseClickListener() {
        binding.ivClose.setOnSingleClickListener {
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
