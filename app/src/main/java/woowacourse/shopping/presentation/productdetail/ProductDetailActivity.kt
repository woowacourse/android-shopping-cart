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
import woowacourse.shopping.data.product.ProductService
import woowacourse.shopping.data.recentproduct.RecentProductDao
import woowacourse.shopping.data.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.dialog.ProductDetailDialog
import woowacourse.shopping.util.getParcelableExtraCompat
import woowacourse.shopping.util.noIntentExceptionHandler

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var activityBinding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var productModel: ProductModel
    private lateinit var productDetailDialog: ProductDetailDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initProductModel()
        initPresenter()
        setUpBinding()
        initView()
    }

    private fun initProductModel() {
        intent.getParcelableExtraCompat<ProductModel>(CART_PRODUCT_KEY_VALUE)
            ?.let { receivedProduct ->
                productModel = receivedProduct
            } ?: noIntentExceptionHandler(getString(R.string.product_model_null_error_message))
    }

    private fun initPresenter() {
        presenter = ProductDetailPresenter(
            view = this,
            cartRepository = CartRepositoryImpl(CartDao(CartDbHelper(this)), ProductService),
            productModel = productModel,
            recentProductRepository = RecentProductRepositoryImpl(
                RecentProductDao(
                    RecentProductDbHelper(this),
                ),
                ProductService,
            ),
        )
    }

    private fun setUpBinding() {
        activityBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        activityBinding.productModel = productModel
        presenter.checkCurrentProductIsMostRecent()
    }

    private fun initView() {
        setToolbar()
        presenter.saveRecentProduct()
        mostRectProductClick()
        putInCartButtonClick()
    }

    private fun setToolbar() {
        setSupportActionBar(activityBinding.toolbarProductDetail.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun mostRectProductClick() {
        activityBinding.cardProductDetailMostRecent.setOnClickListener {
            presenter.showMostRecentProductDetail()
        }
    }

    private fun putInCartButtonClick() {
        activityBinding.buttonPutInCart.setOnClickListener {
            presenter.showProductCart()
        }
    }

    override fun navigateToMostRecent(productModel: ProductModel) {
        val intent = getIntent(
            this,
            productModel,
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun showProductCart(cartProductModel: CartProductInfoModel) {
        productDetailDialog = ProductDetailDialog(
            cartProductModel = cartProductModel,
            presenter = presenter,
        )
        productDetailDialog.show(supportFragmentManager, "ProductDetailDialog")
    }

    override fun setTotalPrice(totalPrice: Int) {
        productDetailDialog.setTotalPrice(totalPrice)
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

    override fun showCompleteMessage(productName: String) {
        Toast.makeText(
            this,
            getString(R.string.put_in_cart_complete_message, productName),
            Toast.LENGTH_SHORT,
        ).show()
    }

    override fun setMostRecentProductVisible(
        visible: Boolean,
        mostRecentProductModel: ProductModel,
    ) {
        if (visible) {
            activityBinding.mostRecentProductModel = mostRecentProductModel
        } else {
            activityBinding.cardProductDetailMostRecent.visibility = View.GONE
        }
    }

    companion object {
        private const val CART_PRODUCT_KEY_VALUE = "CART_PRODUCT_KEY_VALUE"

        fun getIntent(context: Context, productModel: ProductModel): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(CART_PRODUCT_KEY_VALUE, productModel)
            }
        }
    }
}
