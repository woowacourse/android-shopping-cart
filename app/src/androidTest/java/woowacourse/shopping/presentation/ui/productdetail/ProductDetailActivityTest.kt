package woowacourse.shopping.presentation.ui.productdetail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.api.DummyData
import woowacourse.shopping.data.repsoitory.remote.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository

@RunWith(AndroidJUnit4::class)
class ProductDetailActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<ProductDetailActivity> =
        ActivityScenarioRule<ProductDetailActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ProductDetailActivity::class.java,
            ).apply {
                val repository: ProductRepository = ProductRepositoryImpl(DummyData.PRODUCT_LIST)
                val pagingProduct = repository.getPagingProduct(0, 20).getOrThrow()
                val id = pagingProduct.first().id
                putExtra(ProductDetailActivity.PUT_EXTRA_PRODUCT_ID, id)
            },
        )

    @Test
    fun `장바구니_담기_버튼을_누르면_스낵바가_띄워진다`() {
        val addToCartBtn: ViewInteraction = onView(withId(R.id.tv_add_to_cart))

        addToCartBtn.perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.add_to_cart_success_message)))
    }
}
