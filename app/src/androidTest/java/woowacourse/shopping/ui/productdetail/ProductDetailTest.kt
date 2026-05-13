package woowacourse.shopping.ui.productdetail

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import org.junit.Test
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.ui.productdetail.screen.ProductDetailScreen

class ProductDetailTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun 장바구니_담기_버튼을_클릭하면_스낵바가_표출된다() =
        runComposeUiTest {
            setContent {
                val packageName = LocalContext.current.packageName

                ProductDetailScreen(
                    product = ProductFixture.productList(packageName).last(),
                    onAddToCart = {},
                    onClose = {},
                )
            }
            onNodeWithText("장바구니 담기").performClick()
        }
}
