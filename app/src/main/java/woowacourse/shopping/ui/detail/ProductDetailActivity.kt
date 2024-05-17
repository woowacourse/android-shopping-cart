package woowacourse.shopping.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.state.UiState
import woowacourse.shopping.ui.utils.urlToImage

class ProductDetailActivity : AppCompatActivity(), CartButtonClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    private var toast: Toast? = null
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(ProductsImpl, CartsImpl)
    }
    private val productId by lazy { productId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        initBinding()

        showProductDetail()
        observeProduct()
        setOnCartButtonClickListener()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun setOnCartButtonClickListener() {
        binding.cartButtonClickListener = this
    }

    private fun showProductDetail() {
        viewModel.loadProduct(productId)
    }

    private fun observeProduct() {
        viewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is UiState.ERROR -> {
                    binding.btnAddProduct.isEnabled = false
                    toast = Toast.makeText(this, uiState.error.message, Toast.LENGTH_SHORT)
                    toast?.show()
                }

                is UiState.SUCCESS -> {}

                is UiState.LOADING -> {}
            }
        }
    }

    override fun onClick() {
        viewModel.addProductToCart()
        toast?.cancel()
        toast = Toast.makeText(this, getString(R.string.add_cart_complete), Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_close -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun productId() =
        intent.getLongExtra(
            ProductDetailKey.EXTRA_PRODUCT_KEY,
            EXTRA_DEFAULT_VALUE,
        )

    companion object {
        private const val EXTRA_DEFAULT_VALUE = -1L

        fun startActivity(
            context: Context,
            productId: Long,
        ) = Intent(context, ProductDetailActivity::class.java).run {
            putExtra(ProductDetailKey.EXTRA_PRODUCT_KEY, productId)
            context.startActivity(this)
        }
    }
}

@BindingAdapter("productName")
fun TextView.setProductName(uiState: UiState<Product>) {
    if (uiState is UiState.SUCCESS) {
        text = uiState.data.name
    }
}

@BindingAdapter("productPrice")
fun TextView.setProductPrice(uiState: UiState<Product>) {
    if (uiState is UiState.SUCCESS) {
        text = context.getString(R.string.product_price, uiState.data.price)
    }
}

@BindingAdapter("imageUrlOnSuccess")
fun ImageView.bindUrlToImageOnSuccess(uiState: UiState<Product>) {
    if (uiState is UiState.SUCCESS) {
        urlToImage(context, uiState.data.imageUrl)
    }
}
