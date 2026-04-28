package woowacourse.shopping.activity

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.ProductDetailActivity
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.ProductListActivity
import kotlin.uuid.ExperimentalUuidApi

class ProductListActivityTest {
    @get:Rule
    val intentsRule = IntentsRule()

    @get:Rule
    val composeRule = createAndroidComposeRule<ProductListActivity>()

    @OptIn(ExperimentalTestApi::class, ExperimentalUuidApi::class)
    @Test
    fun openProductDetailWhenProductClicked() {
        val product = ProductFixture.productList.first()

        composeRule
            .onNodeWithText(product.productName)
            .performClick()

        intended(hasComponent(ProductDetailActivity::class.java.name))
        intended(hasExtra("woowacourse.shopping.product_id", product.productId.toString()))
        intended(hasExtra("woowacourse.shopping.product_name", product.productName))
        intended(hasExtra("woowacourse.shopping.image_url", product.imageUrl))
        intended(hasExtra("woowacourse.shopping.price", product.price.value))
    }
}