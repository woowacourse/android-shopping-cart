package woowacourse.shopping.presentation.ui.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.presentation.ui.detail.ProductDetailActivity.Companion.EXTRA_PRODUCT_ID
import woowacourse.shopping.presentation.ui.dummyProduct
import java.lang.IllegalStateException

@RunWith(AndroidJUnit4::class)
class ProductDetailActivityTest {
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            ProductDetailActivity::class.java,
        ).apply { putExtra(EXTRA_PRODUCT_ID, 1L) }

    @get:Rule
    val activityRule = ActivityScenarioRule<ProductDetailActivity>(intent)

    @Test
    fun `선택된_상품의_이미지가_보인다`() {
        onView(withId(R.id.iv_product)).check(
            matches(isDisplayed()),
        )
    }

    @Test
    fun `선택된_상품의_제목이_보인다`() {
        onView(withId(R.id.tv_name)).check(
            matches(isDisplayed()),
        )
    }

    @Test
    fun `선택된_상품의_가격이_보인다`() {
        onView(withId(R.id.tv_price_value)).check(
            matches(isDisplayed()),
        )
    }

    @Test
    fun `장바구니에_담으면_상품이_데이터에_추가된다`() {
        onView(withId(R.id.tv_add_cart)).perform(click())
        DummyCartRepository.load(0, 1).onSuccess {
            it.size shouldBe 1
        }.onFailure {
            throw IllegalStateException()
        }
    }
}
