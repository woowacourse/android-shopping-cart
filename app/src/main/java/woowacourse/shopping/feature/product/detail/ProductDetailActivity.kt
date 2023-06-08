package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.repository.CartRepository
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.MockProductRemoteService
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.product.model.ProductState
import woowacourse.shopping.feature.product.recent.model.RecentProductState
import woowacourse.shopping.util.extension.showToast

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private var _binding: ActivityProductDetailBinding? = null
    private val binding: ActivityProductDetailBinding
        get() = _binding!!

    private val presenter: ProductDetailContract.Presenter by lazy {
        val product: ProductState? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }
        val recentProduct: RecentProductState? by lazy {
            intent.getParcelableExtra(
                RECENT_PRODUCT_KEY
            )
        }
        val cartRepository: CartRepository =
            CartRepositoryImpl(MockProductRemoteService(), CartDao(this))
        ProductDetailPresenter(this, product, recentProduct, cartRepository)
    }

    private val countSelectorDialog: CountSelectorDialog by lazy {
        CountSelectorDialog(
            context = this,
            plusCount = { presenter.plusCount() },
            minusCount = { presenter.minusCount() },
            addCartProduct = { presenter.addCartProduct(it) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.loadProduct()
        presenter.loadRecentProduct()
        binding.addCartProductTv.setOnClickListener { presenter.navigateSelectCountDialog() }
        binding.mostRecentProductLayout.setOnClickListener { presenter.navigateProductDetail() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showCart() {
        CartActivity.startActivity(this)
    }

    override fun setViewContent(product: ProductState) {
        binding.product = product
    }

    override fun setMostRecentViewContent(recentProductState: RecentProductState?) {
        if (recentProductState == null) binding.mostRecentProductLayout.visibility = GONE
        else binding.mostRecentProduct = recentProductState
    }

    override fun setDialogCount(count: Int) {
        countSelectorDialog.setCount(count)
    }

    override fun showAccessError() {
        showToast(getString(R.string.error_intent_message))
    }

    override fun showSelectCountDialog(productState: ProductState) {
        countSelectorDialog.show(productState)
    }

    override fun showProductDetail(product: ProductState) {
        startActivity(this, product)
    }

    override fun closeProductDetail() {
        finish()
    }

    companion object {
        private const val PRODUCT_KEY = "product"
        private const val RECENT_PRODUCT_KEY = "recent_product"

        fun startActivity(
            context: Context,
            product: ProductState,
            recentProduct: RecentProductState? = null
        ) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
                putExtra(RECENT_PRODUCT_KEY, recentProduct)
            }
            context.startActivity(intent)
        }
    }
}
