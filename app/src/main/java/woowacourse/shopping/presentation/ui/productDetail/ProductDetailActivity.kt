package woowacourse.shopping.presentation.ui.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.recentlyViewed.RecentlyViewedDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailContract
import woowacourse.shopping.presentation.ui.productDetail.presenter.ProductDetailPresenter
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    override val presenter: ProductDetailContract.Presenter by lazy { initPresenter() }
    private fun initPresenter(): ProductDetailPresenter {
        return ProductDetailPresenter(
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
        presenter.getProduct(productId)
        binding.presenter = presenter as ProductDetailPresenter
    }

    private fun addRecentlyViewedProduct(productId: Long) =
        presenter.addRecentlyViewedProduct(productId, COUNT_TEN)

    private fun setClickEvent() {
        setClickEventOnCloseButton()
        setClickEventOnDibsButton()
    }

    private fun setClickEventOnCloseButton() {
        binding.ivDetailCloseButton.setOnClickListener { finish() }
    }

    private fun setClickEventOnDibsButton() {
        binding.btnDetailAddButton.setOnClickListener {
            presenter.addProductInCart()
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
    }

    companion object {
        private const val DEFAULT_ID: Long = 0
        private const val COUNT_TEN = 10
        private const val PRODUCT_ID = "productId"
        fun getIntent(context: Context, productId: Long): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
        }
    }
}
