package woowacourse.shopping.feature.product.detail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.repository.CartRepository
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.databinding.DialogSelectCountBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.util.extension.showToast

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private var _binding: ActivityProductDetailBinding? = null
    private val binding: ActivityProductDetailBinding
        get() = _binding!!

    private val presenter: ProductDetailContract.Presenter by lazy {
        val product: ProductState? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }
        val cartRepository: CartRepository = CartRepositoryImpl(CartDao(this))
        ProductDetailPresenter(this, product, cartRepository)
    }
    private val selectCountDialogBinding: DialogSelectCountBinding by lazy {
        DialogSelectCountBinding.inflate(LayoutInflater.from(this), null, false)
    }
    private val selectCountDialog: Dialog by lazy {
        selectCountDialogBinding.product = presenter.product
        AlertDialog.Builder(this).apply {
            setView(selectCountDialogBinding.root)
            selectCountDialogBinding.counterView.count = MIN_COUNT_VALUE
            selectCountDialogBinding.counterView.plusClickListener = { presenter.plusCount() }
            selectCountDialogBinding.counterView.minusClickListener = { presenter.minusCount() }
            selectCountDialogBinding.addToCartBtn.setOnClickListener {
                presenter.addCartProduct(selectCountDialogBinding.counterView.count)
            }
        }.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.loadProduct()
        binding.addCartProductTv.setOnClickListener { showSelectCountDialog() }
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

    override fun setCount(count: Int) {
        selectCountDialogBinding.counterView.count = count
    }

    override fun showAccessError() {
        showToast(getString(R.string.error_intent_message))
    }

    override fun showSelectCountDialog() {
        selectCountDialog.show()
    }

    override fun closeProductDetail() {
        finish()
    }

    companion object {
        private const val PRODUCT_KEY = "product"

        fun startActivity(context: Context, product: ProductState) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
            }
            context.startActivity(intent)
        }
    }
}
