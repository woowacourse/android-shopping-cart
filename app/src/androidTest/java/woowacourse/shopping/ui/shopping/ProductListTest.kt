package woowacourse.shopping.ui.shopping

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.R
import woowacourse.shopping.domain.Products
import woowacourse.shopping.ui.shopping.screen.ProductListScreen
import kotlin.uuid.ExperimentalUuidApi

class ProductListTest {
    @OptIn(ExperimentalTestApi::class, ExperimentalUuidApi::class)
    @Test
    fun 정상적으로_product_데이터를_불러올_수_있다() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val products = ProductFixture.productList(context.packageName)
        val targetTag = "product_item_${products.first().productId}"

        runComposeUiTest {
            setContent {
                ProductListScreen(
                    products = Products(products),
                    onCartClick = {},
                    onProductClick = {},
                )
            }

            onNodeWithTag(targetTag).assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class, ExperimentalUuidApi::class)
    @Test
    fun 더보기_버튼을_통해서_product_데이터를_추가적으로_불러올_수_있다() =
        runComposeUiTest {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val products = ProductFixture.productList(context.packageName)
            val hiddenProductTag = "product_item_${products[20].productId}"
            val seeMoreText = context.getString(R.string.see_more)

            setContent {
                ProductListScreen(
                    products = Products(products),
                    onCartClick = {},
                    onProductClick = {},
                )
            }

            onNodeWithTag(hiddenProductTag).assertDoesNotExist()
            onNodeWithTag("product_grid").performScrollToNode(hasText(seeMoreText))
            onNodeWithText(seeMoreText).performClick()
            onNodeWithTag("product_grid").performScrollToNode(hasTestTag(hiddenProductTag))
            onNodeWithTag(hiddenProductTag).assertIsDisplayed()
        }
}
