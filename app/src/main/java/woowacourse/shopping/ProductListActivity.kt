@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.ProductListScreen
import woowacourse.shopping.ui.pagination.ProductDataLoadStateHolder
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ProductListActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productPaginationStateHolder =
                    remember {
                        ProductDataLoadStateHolder(productRepository.getProducts())
                    }
                ProductListScreen(
                    products = productPaginationStateHolder.getItems(),
                    onProductClick = { productId ->
                        val intent = Intent(this, DetailProductActivity::class.java)
                        intent.putExtra("productId", productId)
                        startActivity(intent)
                    },
                    onNavigateToCartClick = {
                        startActivity(Intent(this, ShoppingCartActivity::class.java))
                    },
                ) {
                    MoreButton(onClick = productPaginationStateHolder::nextPage)
                }
            }
        }
    }
}

@Composable
fun MoreButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color.Transparent,
            ),
        modifier =
            Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 10.dp).border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        Text("더보기")
    }
}
