package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.repository.CartRepository
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.MockProductRemoteService
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.databinding.DialogSelectCountBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.RecentProductState
import woowacourse.shopping.util.extension.showToast

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private var _binding: ActivityProductDetailBinding? = null
    private val binding: ActivityProductDetailBinding
        get() = _binding!!

    private val presenter: ProductDetailContract.Presenter by lazy {
        val product: ProductState? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }
        val recentProduct: RecentProductState? by lazy { intent.getParcelableExtra(RECENT_PRODUCT_KEY) }
        val cartRepository: CartRepository = CartRepositoryImpl(MockProductRemoteService(), CartDao(this))
        ProductDetailPresenter(this, product, recentProduct, cartRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.loadProduct()
        presenter.loadRecentProduct()
        binding.addCartProductTv.setOnClickListener { presenter.selectCount() }
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

    override fun setCount(selectCountDialogBinding: DialogSelectCountBinding, count: Int) {
        selectCountDialogBinding.counterView.count = count
    }

    override fun showAccessError() {
        showToast(getString(R.string.error_intent_message))
    }

    override fun showSelectCountDialog() {
        createSelectCountDialog().show()
    }

    override fun showProductDetail(product: ProductState) {
        startActivity(this, product)
    }

    override fun closeProductDetail() {
        finish()
    }

    private fun createSelectCountDialog(): AlertDialog {
        val selectCountDialogBinding: DialogSelectCountBinding =
            DialogSelectCountBinding.inflate(LayoutInflater.from(this))
        selectCountDialogBinding.product = presenter.product

        return AlertDialog.Builder(this).apply {
            setView(selectCountDialogBinding.root)
            selectCountDialogBinding.counterView.count = MIN_COUNT_VALUE
            selectCountDialogBinding.counterView.plusClickListener =
                { presenter.plusCount(selectCountDialogBinding) }
            selectCountDialogBinding.counterView.minusClickListener =
                { presenter.minusCount(selectCountDialogBinding) }
            selectCountDialogBinding.addToCartBtn.setOnClickListener {
                runOnUiThread { presenter.addCartProduct(selectCountDialogBinding.counterView.count) }
            }
        }.create()
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
