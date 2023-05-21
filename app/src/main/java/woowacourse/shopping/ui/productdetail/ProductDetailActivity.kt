package woowacourse.shopping.ui.productdetail

import android.app.Activity
import android.app.AlertDialog
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
import woowacourse.shopping.databinding.DialogProductDetailBinding
import woowacourse.shopping.ui.basket.BasketActivity
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.shopping.ShoppingActivity
import woowacourse.shopping.util.getParcelableExtraCompat
import woowacourse.shopping.util.intentDataNullProcess
import woowacourse.shopping.util.setThrottleFirstOnClickListener

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var dialogViewBinding: DialogProductDetailBinding
    private lateinit var alertDialog: AlertDialog
    private lateinit var currentProduct: UiProduct
    private var previousProduct: UiProduct? = null
    private lateinit var presenter: ProductDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSetResult()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        if (!initExtraData()) return
        initPresenter()
        initBasketButtonClickListener()
        initPreviousProductClickListener()
        presenter.initProductData()
        initButtonCloseClickListener()
    }

    private fun initSetResult() {
        setResult(Activity.RESULT_OK, ShoppingActivity.getResultIntent())
    }

    private fun initBasketButtonClickListener() {
        binding.addMarketClickListener = presenter::setBasketDialog
    }

    override fun showBasketDialog(
        currentProduct: UiProduct,
        minusClickListener: () -> Unit,
        plusClickListener: () -> Unit,
        updateBasketProduct: () -> Unit
    ) {
        dialogViewBinding = DialogProductDetailBinding.inflate(layoutInflater)
        alertDialog = AlertDialog.Builder(this)
            .setView(dialogViewBinding.root)
            .create()

        dialogViewBinding.product = currentProduct
        dialogViewBinding.dialogCounter.minusClickListener = { _ -> minusClickListener() }
        dialogViewBinding.dialogCounter.plusClickListener = { _ -> plusClickListener() }
        dialogViewBinding.addBasketClickListener = updateBasketProduct

        alertDialog.show()
    }

    override fun updateProductCount(count: Int) {
        dialogViewBinding.dialogCounter.count = count
    }

    private fun initPreviousProductClickListener() {
        binding.previousProductClickListener = presenter::selectPreviousProduct
    }

    private fun initExtraData(): Boolean {
        currentProduct = intent.getParcelableExtraCompat(CURRENT_PRODUCT_KEY)
            ?: return intentDataNullProcess(CURRENT_PRODUCT_KEY)
        previousProduct = intent.getParcelableExtraCompat(PREVIOUS_PRODUCT_KEY)
        return true
    }

    override fun updateBindingData(product: UiProduct, previousProduct: UiProduct?) {
        binding.product = product
        binding.previousProduct = previousProduct
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
        alertDialog.dismiss()
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
