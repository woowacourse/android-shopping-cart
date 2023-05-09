package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.Product
import woowacourse.shopping.R
import woowacourse.shopping.feature.mapper.toUi
import woowacourse.shopping.feature.model.ProductState

class ProductDetailActivity : AppCompatActivity() {
    private val product: ProductState by lazy { intent.getParcelableExtra(PRODUCT_KEY) ?: Product.EMPTY.toUi() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
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
