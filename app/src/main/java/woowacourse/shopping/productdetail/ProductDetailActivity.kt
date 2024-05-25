package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.GetLastProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.util.ViewModelFactory
import woowacourse.shopping.util.imageUrlToSrc
import woowacourse.shopping.util.showToastMessage

class ProductDetailActivity : AppCompatActivity(), ProductDetailClickAction {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory() }
    private var productId: Long = INVALID_PRODUCT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productId = intent.getLongExtra(EXTRA_PRODUCT_ID, INVALID_PRODUCT_ID)
        binding.lifecycleOwner = this
        binding.onClick = this
        binding.productId = productId
        showProductDetail(productId)
        onBackPressedCallbackInit()
        loadRecentProduct()

        supportActionBar?.setDisplayShowTitleEnabled(false)

        navigateToProductList(productId)
    }

    private fun loadRecentProduct()  {
        viewModel.loadRecentProduct()
        viewModel.recentProductState.observe(this) { state ->
            when (state) {
                is GetLastProduct.Success -> binding.tvProductDetailLastProductName.text = state.value.name
                GetLastProduct.Fail -> binding.containerProductDetailLastProduct.isVisible = false
            }
        }
    }

    private fun navigateToProductList(productId: Long) {
        viewModel.isAddSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                val intent = ProductListActivity.changedProductIntent(this, longArrayOf(productId))
                this.setResult(RESULT_OK, intent)
                this.finish()
            }
        }
    }

    private fun showProductDetail(productId: Long) {
        viewModel.loadProductDetail(productId)
        viewModel.product.observe(this) {
            showProductDetailView(it)
        }
        viewModel.countState.observe(this) { countState ->
            when (countState) {
                is CountState.ChangeItemCount -> binding.countResult = countState.countResult
                is CountState.MinusFail -> showToastMessage(R.string.min_cart_item_message)
                is CountState.PlusFail -> showToastMessage(R.string.max_cart_item_message)
                is CountState.ShowCount -> binding.countResult = countState.countResult
            }
        }
    }

    private fun showProductDetailView(product: Product) {
        with(binding) {
            imageUrlToSrc(product.imageUrl.url, ivProductDetailProduct)
            tvProductDetailName.text = product.name
            tvProductDetailPrice.text =
                getString(R.string.product_price_format, product.price.value)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_detail_close -> {
                updateInfoChange()
            }
            else -> {}
        }
        return true
    }

    override fun onAddCartClickAction() {
        viewModel.addProductToCart()
    }

    override fun onPlusCountClicked(id: Long) {
        viewModel.plusProductCount()
    }

    override fun onMinusCountClicked(id: Long) {
        viewModel.minusProductCount()
    }

    private fun onBackPressedCallbackInit() {
        onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    updateInfoChange()
                }
            }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun updateInfoChange()  {
        viewModel.updateRecentProduct(productId)
        val intent =
            ProductListActivity.recentInstance(this@ProductDetailActivity, true)
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "productId"
        private const val INVALID_PRODUCT_ID = -1L

        fun newInstance(
            context: Context,
            productId: Long,
        ) = Intent(context, ProductDetailActivity::class.java).apply {
            this.putExtra(
                EXTRA_PRODUCT_ID,
                productId,
            )
        }
    }
}
