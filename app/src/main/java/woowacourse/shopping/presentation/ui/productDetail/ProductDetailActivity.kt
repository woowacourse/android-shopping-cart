package woowacourse.shopping.presentation.ui.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailContract
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailPresenter
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private val dialog by lazy { ProductDetailCustomDialog(this) }
    override val presenter: ProductDetailContract.Presenter by lazy { initPresenter() }
    private fun initPresenter(): ProductDetailPresenter {
        return ProductDetailPresenter(
            this,
            ProductRepositoryImpl(
                productDataSource = ProductDao(this),
                recentlyViewedDataSource = RecentlyViewedDao(this),
            ),
            ShoppingCartRepositoryImpl(
                shoppingCartDataSource = ShoppingCartDao(this),
                productDataSource = ProductDao(this),
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
            override fun setClickEventOnToShoppingCart(
                product: ProductInCartUiState,
                onClick: ProductDetailClickListener,
            ) {
                initDialog(product, onClick)
//                presenter.addProductInCart(product)
                // navigateToShoppingCartActivity()
            }

            override fun setClickEventOnLastViewed(lastViewedProduct: Product) {
                navigateToNewProductDetailActivity(lastViewedProduct)
            }

            override fun setClickEventOnOperatorButton(
                operator: Boolean,
                productInCart: ProductInCartUiState,
            ) {
                Log.d("123123", "123123")
            }
        }
    }

    private fun initDialog(product: ProductInCartUiState, onClick: ProductDetailClickListener) {
        dialog.onCreate(product, onClick)
    }

    private fun navigateToShoppingCartActivity() =
        startActivity(ShoppingCartActivity.getIntent(this))

    private fun navigateToNewProductDetailActivity(lastViewedProduct: Product) {
        val intent = getIntent(this, lastViewedProduct.id, true)
        startActivity(intent)
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
