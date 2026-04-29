package woowacourse.shopping.feature.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import woowacourse.shopping.core.data.CartRepository
import woowacourse.shopping.core.data.ProductData
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.detail.ui.DetailScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val id = this.intent.getStringExtra("id")
            require(id != null) {
                Toast.makeText(this, "유효하지 않은 상품입니다.", Toast.LENGTH_SHORT).show()
                this.finish()
            }
            val product = ProductData.getProductById(id)
            AndroidshoppingTheme {
                DetailScreen(
                    product = product,
                    onAddProductToCart = {
                        if (CartRepository.addItem(product)) {
                            val intent = Intent(this, CartActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "장바구니 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier,
                )
            }
        }
    }
}
