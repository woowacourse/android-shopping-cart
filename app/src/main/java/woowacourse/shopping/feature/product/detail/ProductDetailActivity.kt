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
import woowacourse.shopping.feature.extension.showToast
import woowacourse.shopping.feature.model.ProductState
import woowacourse.shopping.feature.model.mapper.toDomain

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    private val product: ProductState? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }
    private val dbHandler: CartDbHandler by lazy {
        CartDbHandler(CartDbHelper(this).writableDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (product == null) {
            showToast(getString(R.string.error_intent_message))
            finish()
        }

        binding.product = product

        product?.let { product ->
            binding.navigateCartTv.setOnClickListener {
                dbHandler.addColumn(product.toDomain())
                CartActivity.startActivity(this)
            }
        }
    }

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
