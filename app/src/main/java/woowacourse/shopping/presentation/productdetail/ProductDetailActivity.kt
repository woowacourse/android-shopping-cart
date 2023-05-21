package woowacourse.shopping.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.MockProductDao
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.util.getParcelableExtraCompat
import woowacourse.shopping.util.noIntentExceptionHandler

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var activityBinding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var productModel: ProductModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        initProductModel()
        initPresenter()
        initView()
    }

    private val showProductQuantitySelectionView = { it: Int ->
        ProductDetailDialog(
            productModel = productModel,
            presenter = presenter,
        ).show(supportFragmentManager, "ProductDetailDialog")
    }

    private fun initView() {
        setToolbar()
        activityBinding.onClickButton = showProductQuantitySelectionView
        activityBinding.productModel = productModel
        activityBinding.presenter = presenter
        presenter.checkMostRecentProduct()
        presenter.saveRecentProduct()
        mostRecentProductClick()
    }

    private fun setToolbar() {
        setSupportActionBar(activityBinding.toolbarProductDetail.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_close -> {
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun initProductModel() {
        intent.getParcelableExtraCompat<ProductModel>(PRODUCT_KEY_VALUE)?.let { recievedProduct ->
            productModel = recievedProduct
        } ?: noIntentExceptionHandler(getString(R.string.product_model_null_error_message))
    }

    private fun initPresenter() {
        presenter = ProductDetailPresenter(
            view = this,
            cartRepository = CartRepositoryImpl(CartDao(CartDbHelper(this)), MockProductDao),
            productModel = productModel,
            recentProductRepository = RecentProductRepositoryImpl(
                RecentProductDao(
                    RecentProductDbHelper(this),
                ),
                MockProductDao,
            ),
        )
    }

    override fun showCompleteMessage(productName: String) {
        Toast.makeText(
            this,
            getString(R.string.put_in_cart_complete_message, productName),
            Toast.LENGTH_SHORT,
        ).show()
    }

    override fun hideMostRecentProduct() {
        activityBinding.cardProductDetailMostRecent.visibility = View.GONE
    }

    private fun mostRecentProductClick() {
        val intent = getIntent(
            this,
            presenter.mostRecentProduct.value.toPresentation(),
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        activityBinding.cardProductDetailMostRecent.setOnClickListener {
            startActivity(intent)
        }
    }

    companion object {
        private const val PRODUCT_KEY_VALUE = "PRODUCT_KEY_VALUE"

        fun getIntent(context: Context, productModel: ProductModel): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY_VALUE, productModel)
            }
        }
    }
}
