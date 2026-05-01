package woowacourse.shopping.activity

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.ProductFixture
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.ProductListActivity
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

        Intents.intended(IntentMatchers.hasComponent(ProductDetailActivity::class.java.name))
        Intents.intended(
            IntentMatchers.hasExtra(
                "woowacourse.shopping.product_id",
                product.productId.toString(),
            ),
        )
    }

    @OptIn(ExperimentalTestApi::class, ExperimentalUuidApi::class)
    @Test
    fun openCartWhenCartIconClicked() {
        composeRule
            .onNodeWithContentDescription("shoppingCart")
            .performClick()

        Intents.intended(IntentMatchers.hasComponent(CartActivity::class.java.name))
    }

    @OptIn(ExperimentalTestApi::class, ExperimentalUuidApi::class)
    @Test
    fun returnToProductListWhenLeftArrowClickedInCart() {
        composeRule
            .onNodeWithContentDescription("shoppingCart")
            .performClick()

        composeRule
            .onNodeWithContentDescription("leftArrowIcon")
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("leftArrowIcon")
            .performClick()

        composeRule
            .onNodeWithText("Shopping")
            .assertIsDisplayed()
    }
}
