package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.data.cart.CartDbHelper
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.util.extension.showToast

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private var _binding: ActivityProductDetailBinding? = null
    private val binding: ActivityProductDetailBinding
        get() = _binding!!

    private val presenter: ProductDetailContract.Presenter by lazy {
        val product: ProductState? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }
        val cartDbHandler = CartDbHandler(CartDbHelper(this).writableDatabase)
        ProductDetailPresenter(this, product, cartDbHandler)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.loadProduct()

        binding.addCartProductTv.setOnClickListener { presenter.addCartProduct() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showCart() { CartActivity.startActivity(this) }

    override fun setViewContent(product: ProductState) { binding.product = product }

    override fun showAccessError() { showToast(getString(R.string.error_intent_message)) }

    override fun closeProductDetail() { finish() }

    companion object {
        const val PRODUCT_KEY = "product"

        fun startActivity(context: Context, product: ProductState) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
            }
            context.startActivity(intent)
        }
    }
}
