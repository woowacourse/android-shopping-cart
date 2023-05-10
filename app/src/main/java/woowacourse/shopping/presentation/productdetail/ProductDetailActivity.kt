package woowacourse.shopping.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbAdapter
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.util.getParcelableExtraCompat
import woowacourse.shopping.util.noIntentExceptionHandler

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productModel: ProductModel
    private val presenter: ProductDetailContract.Presenter by lazy {
        ProductDetailPresenter(this, CartDbAdapter(CartDbHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receiveProductModel()
        initView()
    }

    private fun initView() {
        setToolbar()
        setProductInfo()
        binding.buttonPutInCart.setOnClickListener { putProductInCart() }
    }

    private fun putProductInCart() {
        presenter.putProductInCart(productModel)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarProductDetail.toolbar)
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

    override fun showCompleteMessage(productName: String) {
        Toast.makeText(
            this,
            getString(R.string.put_in_cart_complete_message, productName),
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun setProductInfo() {
        binding.textProductDetailPrice.text = getString(R.string.price_format, productModel.price)
        binding.textProductDetailName.text = productModel.name
        setProductImage()
    }

    private fun setProductImage() {
        Glide.with(this)
            .load(productModel.imageUrl)
            .error(R.drawable.default_image)
            .centerCrop()
            .into(binding.imageProductDetailPoster)
    }

    private fun receiveProductModel() {
        productModel = intent.getParcelableExtraCompat(PRODUCT_KEY_VALUE)
            ?: return this.noIntentExceptionHandler(getString(R.string.product_model_null_error_message))
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
