package woowacourse.shopping.feature.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.cart.model.AddItemResult
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
            AndroidshoppingTheme {
                DetailScreen(
                    id = id,
                    onNavigateToCart = { result ->
                        when (result) {
                            is AddItemResult.NewAdded -> {
                                val intent = Intent(this, CartActivity::class.java)
                                startActivity(intent)
                            }
                            is AddItemResult.DuplicateItem ->
                                Toast.makeText(this, "이미 장바구니에 담긴 상품입니다.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier,
                )
            }
        }
    }
}
