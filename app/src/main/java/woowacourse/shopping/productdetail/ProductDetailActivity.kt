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
import woowacourse.shopping.domain.Product
import woowacourse.shopping.productdetail.uimodel.CountEvent
import woowacourse.shopping.productdetail.uimodel.ProductDetailClickAction
import woowacourse.shopping.productdetail.uimodel.RecentProductState
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.productlist.ProductListActivity.ResultActivity.Companion.ACTIVITY_TYPE
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
        showProductDetail()
        onBackPressedCallbackInit()
        loadRecentProduct()
        navigateToProductList()

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun loadRecentProduct() {
        viewModel.loadRecentProduct()
        viewModel.recentProductState.observe(this) { state ->
            when (state) {
                is RecentProductState.NoRecentProduct, RecentProductState.Same,
                -> binding.containerProductDetailLastProduct.isVisible = false

                is RecentProductState.Show,
                -> binding.tvProductDetailLastProductName.text = state.name
            }
        }
    }

    private fun navigateToProductList() {
        viewModel.isAddSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                viewModel.updateRecentProduct(productId)
                val intent =
                    Intent(this, ProductListActivity::class.java).apply {
                        putExtra(CHANGED_PRODUCT_ID, longArrayOf(productId))
                        putExtra(IS_RECENT_CHANGED, true)
                        putExtra(ACTIVITY_TYPE, ProductListActivity.ResultActivity.DETAIL.ordinal)
                    }

                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun showProductDetail() {
        viewModel.initProductDetail(productId)
        viewModel.product.observe(this) {
            showProductDetailView(it)
        }

        viewModel.count.observe(this) {
            binding.countResult = it
        }

        viewModel.countEvent.observe(this) { event ->
            when (event) {
                CountEvent.MinusFail -> showToastMessage(R.string.min_cart_item_message)
                CountEvent.PlusFail -> showToastMessage(R.string.max_cart_item_message)
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
            R.id.menu_product_detail_close -> updateRecentInfoChangeAndFinish()
            else -> {}
        }
        return true
    }

    override fun onAddCartClicked() {
        viewModel.addProductToCart()
    }

    override fun onLastProductClicked() {
        val state = viewModel.recentProductState.value
        if (state is RecentProductState.Show) {
            startActivity(newIntent(this, state.id))
            finish()
        }
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
                    updateRecentInfoChangeAndFinish()
                }
            }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun updateRecentInfoChangeAndFinish() {
        viewModel.updateRecentProduct(productId)
        val intent =
            Intent(this, ProductListActivity::class.java).apply {
                putExtra(IS_RECENT_CHANGED, true)
                putExtra(ACTIVITY_TYPE, ProductListActivity.ResultActivity.DETAIL.ordinal)
            }

        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val IS_RECENT_CHANGED = "isRecentChange"
        const val CHANGED_PRODUCT_ID = "productId"

        private const val EXTRA_PRODUCT_ID = "productId"
        private const val INVALID_PRODUCT_ID = -1L

        fun newIntent(
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
