package woowacourse.shopping.presentation.ui.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.dataSource.local.product.ProductDao
import woowacourse.shopping.data.dataSource.local.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.productDetail.dialog.ProductDetailCustomDialog
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailContract
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailPresenter
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    override val presenter: ProductDetailContract.Presenter by lazy { initPresenter() }
    private var _dialog: ProductDetailCustomDialog? = null
    val dialog: ProductDetailCustomDialog get() = _dialog ?: error("Dialog leak")

    private fun initPresenter(): ProductDetailPresenter {
        return ProductDetailPresenter(
            this,
            ProductRepositoryImpl(
                productDataSource = ProductDao(this),
                recentlyViewedDataSource = RecentlyViewedDao(this),
            ),
        )
    }

    override fun setProduct(product: ProductInCartUiState) {
        binding.product = product
    }

    override fun setLastViewedProduct(result: WoowaResult<Product>) {
        when (result) {
            is WoowaResult.SUCCESS -> {
                if (checkStackState()) return
                binding.clDetailLastViewed.visibility = View.VISIBLE
                binding.lastViewedProduct = result.data
            }

            is WoowaResult.FAIL -> binding.clDetailLastViewed.visibility = View.INVISIBLE
        }
    }

    private fun checkStackState(): Boolean {
        if (intent.getBooleanExtra(IS_STACKED, false)) {
            binding.clDetailLastViewed.visibility = View.INVISIBLE
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getLongExtra(PRODUCT_ID, DEFAULT_ID)

        initView(productId)
        addRecentlyViewedProduct(productId)
        setClickEvent()
    }

    private fun initView(productId: Long) {
        presenter.fetchProduct(productId)
        presenter.fetchLastViewedProduct()
    }

    private fun addRecentlyViewedProduct(productId: Long) =
        presenter.addRecentlyViewedProduct(productId, DEFAULT_UNIT_RECENT_PRODUCT)

    private fun setClickEvent() {
        setClickEventOnCloseButton()
        setClickEventOnToShoppingCartButton()
    }

    private fun setClickEventOnCloseButton() {
        binding.ivDetailCloseButton.setOnClickListener { finish() }
    }

    private fun setClickEventOnToShoppingCartButton() {
        binding.setClickListener = object : ProductDetailClickListener {
            override fun setClickEventOnToShoppingCart(product: ProductInCartUiState) {
                initDialog(product)
            }

            override fun setClickEventOnLastViewed(lastViewedProduct: Product) {
                navigateToNewProductDetailActivity(lastViewedProduct)
            }
        }
    }

    private fun initDialog(product: ProductInCartUiState) {
        _dialog = ProductDetailCustomDialog(this)
        dialog.onCreate(product)
    }

    private fun navigateToNewProductDetailActivity(lastViewedProduct: Product) {
        val intent = getIntent(this, lastViewedProduct.id, true)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _dialog = null
    }

    companion object {
        private const val DEFAULT_ID: Long = 0
        private const val DEFAULT_UNIT_RECENT_PRODUCT = 10
        private const val PRODUCT_ID = "productId"
        private const val IS_STACKED = "isStacked"
        fun getIntent(context: Context, productId: Long, isStacked: Boolean): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
                putExtra(IS_STACKED, isStacked)
            }
        }
    }
}
