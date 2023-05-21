package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.common.CountPickerListener
import woowacourse.shopping.database.ShoppingCacheImpl
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.handleMissingSerializableData

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var dialog: ProductCountPickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        setUpPresenter()
        setUpProductDetailToolbar()
    }

    private fun setUpPresenter() {
        intent.getSerializableCompat<ProductUiModel>(PRODUCT_KEY)?.let {
            presenter = ProductDetailPresenter(
                view = this,
                product = it,
                latestViewedProduct = intent.getSerializableCompat(LATEST_VIEWED_PRODUCT_KEY),
                shoppingCache = ShoppingCacheImpl(
                    shoppingDao = ShoppingDao(this)
                )
            )
        } ?: return handleMissingSerializableData()
    }

    private fun setUpProductDetailToolbar() {
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override val productDetailNavigator: ProductDetailNavigator by lazy {
        ProductDetailNavigatorImpl(this)
    }

    override fun setUpProductDetailView(
        product: ProductUiModel,
    ) {
        setUpProductCountPickerDialog(product)
        binding.product = product
        with(binding) {
            layoutLatestViewedProduct.setOnClickListener {
                presenter.loadLatestViewedProduct()
            }
            buttonPutToShoppingCart.setOnClickListener {
                dialog.show(supportFragmentManager, ProductCountPickerDialog.TAG)
            }
        }
    }

    override fun setUpLatestViewedProductView(product: ProductUiModel?) {
        product?.let {
            binding.latestViewedProduct = product
        } ?: run {
            binding.layoutLatestViewedProduct.isVisible = false
        }
    }

    private fun setUpProductCountPickerDialog(product: ProductUiModel) {
        val countPickerListenerImpl = object : CountPickerListener {

            override fun onPlus() {
                presenter.plusCartProductCount()
            }

            override fun onMinus() {
                presenter.minusCartProductCount()
            }
        }
        val addingCartListenerImpl = object : AddingCartListener {

            override fun onAdded() {
                presenter.addToCart()
            }
        }

        dialog = ProductCountPickerDialog.newInstance(
            product = product,
            countPickerListener = countPickerListenerImpl,
            addingCartListener = addingCartListenerImpl
        )
    }

    override fun setUpDialogTotalPriceView(totalPrice: Int) {
        dialog.setTextTotalPrice(totalPrice)
    }

    companion object {
        private const val PRODUCT_KEY = "product"
        private const val LATEST_VIEWED_PRODUCT_KEY = "latest_viewed_product"

        fun getIntent(
            context: Context,
            product: ProductUiModel,
            latestViewedProduct: ProductUiModel?,
        ): Intent {

            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
                putExtra(LATEST_VIEWED_PRODUCT_KEY, latestViewedProduct)
            }
        }
    }
}
