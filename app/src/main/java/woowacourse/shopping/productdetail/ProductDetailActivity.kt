package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    // todo 어디서 해야할까 result laucher를 사용해야할까?
    //
//    private val latestViewedProductResultLauncher = registerForActivityResult(
//    ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//    if (result.resultCode == Activity.RESULT_OK) {
//    finish()
//    }
//    }

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

        binding.textProductName.text = product.name
        binding.textProductPrice.text = product.price.toString()
        binding.layoutLatestViewedProduct.setOnClickListener {
            Log.d("woogi", "setUpProductDetailView: click")
            navigateToLatestViewedProductView()
        }
    }

    override fun setUpLatestViewedProductView(product: ProductUiModel) {
        binding.textLatestViewProductName.text = product.name
        binding.textLatestViewedProductPrice.text = product.price.toString()
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
        private const val LATEST_VIEWED_PRODUCT_KEY = "latest_viewed_product"

        fun getIntent(
            context: Context,
            product: ProductUiModel,
            latestViewedProduct: ProductUiModel? = null,
        ): Intent {

            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
                latestViewedProduct?.let {
                    putExtra(LATEST_VIEWED_PRODUCT_KEY, it)
                }
            }
        }
    }
}
