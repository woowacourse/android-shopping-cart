package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.feature.extension.showToast
import woowacourse.shopping.feature.model.ProductState

class ProductDetailActivity : AppCompatActivity() {
    private val product: ProductState? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        if (product == null) {
            showToast(getString(R.string.error_intent_message))
            finish()
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
