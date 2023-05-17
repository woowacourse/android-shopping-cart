package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
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
                repository = ShoppingDBAdapter(
                    shoppingDao = ShoppingDao(this)
                )
            )
        } ?: return handleMissingSerializableData()

        binding.presenter = presenter
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

    override fun setUpProductDetailView(
        product: ProductUiModel,
        navigateToLatestViewedProductView: () -> Unit,
    ) {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.imageProductDetail)

        setUpProductCountPickerDialog(product)
        with(binding) {
            textProductName.text = product.name
            textProductPrice.text = product.price.toString()
            layoutLatestViewedProduct.setOnClickListener {
                navigateToLatestViewedProductView()
            }
            buttonPutToShoppingCart.setOnClickListener {
                dialog.show(
                    supportFragmentManager, ProductCountPickerDialog.TAG
                )
            }
        }
    }

    override fun setUpLatestViewedProductView(product: ProductUiModel) {
        binding.textLatestViewProductName.text = product.name
        binding.textLatestViewedProductPrice.text = product.price.toString()
    }

    private fun setUpProductCountPickerDialog(product: ProductUiModel) {
        val productCountPickerListenerImpl = object : ProductCountPickerListener {
            override fun onPlus(count: Int) {
                presenter.plusShoppingCartProductCount(count)
            }

            override fun onMinus(count: Int) {
                presenter.minusShoppingCartProductCount(count)
            }

            override fun onCompleted() {
                finish()
                presenter.addToShoppingCart()
            }
        }

        dialog = ProductCountPickerDialog.newInstance(
            product = product,
            listener = productCountPickerListenerImpl
        )
    }

    override fun setUpDialogProductCountView(count: Int) {
        dialog.setTextProductCount(count)
    }

    override fun setUpDialogTotalPriceView(totalPrice: Int) {
        dialog.setTextTotalPrice(totalPrice)
    }

    override fun navigateToShoppingCartView() {
        startActivity(
            ShoppingCartActivity.getIntent(this)
        )
        finish()
    }

    override fun navigateToProductDetailView(product: ProductUiModel) {
        startActivity(
            getIntent(this, product)
        )
    }

    companion object {
        private const val PRODUCT_KEY = "product"

        fun getIntent(context: Context, product: ProductUiModel): Intent {

            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
            }
        }
    }
}
