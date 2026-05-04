@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import woowacourse.shopping.ui.ProductDto
import woowacourse.shopping.ui.ProductListScreen
import woowacourse.shopping.ui.component.MoreButton
import woowacourse.shopping.ui.pagination.ProductPageStateHolder
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ProductListActivity : ComponentActivity() {
    private val productRepository = ShoppingApplication.productRepository

    companion object {
        const val EXTRA_PRODUCT_ID = "productId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                val productPaginationStateHolder =
                    remember {
                        ProductPageStateHolder(productRepository.getProducts())
                    }
                ProductListScreen(
                    products =
                        productPaginationStateHolder.getItems().map {
                            ProductDto(
                                id = it.id,
                                title = it.getTitle(),
                                price = DecimalFormat(stringResource(R.string.price_format_pattern)).format(it.getPrice()),
                                imageUrl = it.imageUrl,
                            )
                        },
                    onProductClick = { productId ->
                        val intent = Intent(this, DetailProductActivity::class.java)
                        intent.putExtra(EXTRA_PRODUCT_ID, productId)
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
