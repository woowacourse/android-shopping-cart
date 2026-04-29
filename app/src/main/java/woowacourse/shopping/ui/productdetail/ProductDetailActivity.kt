package woowacourse.shopping.ui.productdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.productdetail.stateholder.ProductDetailStateHolder
import woowacourse.shopping.ui.productdetail.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.productlist.ProductDetailScreen
import woowacourse.shopping.ui.state.ProductUiModel

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getStringExtra("id")
        val holder = ProductDetailStateHolder()
        lateinit var productModel: ProductUiModel
        runCatching { holder.getProductUiModel(id) }
            .onSuccess { productModel = it }
            .onFailure { this.finish() }

        setContent {
            AndroidshoppingcartTheme {
                ProductDetailScreen(
                    imageUrl = productModel.imageUrl,
                    title = productModel.title,
                    price = productModel.price,
                    onCloseClick = {
                        finish()
                    },
                )
            }
        }
    }
}
