package woowacourse.shopping.view.detail

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.view.detail.DetailFragment

class DetailFragmentTest {
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        launchFragmentInContainer(
            DetailFragment.newBundle(dummyProductsFixture[0].toUiModel()),
            themeResId = R.style.Theme_Shopping,
        ) { DetailFragment() }
    }

    @Test
    fun `상품의_이름_가격_이미지를_확인할_수_있다`() {
        onView(withId(R.id.text_view_detail_product_name))
            .check(matches(withText(dummyProductsFixture[0].name)))

        val expectedPrice =
            context.getString(R.string.product_price_format, dummyProductsFixture[0].price.value)
        onView(withId(R.id.text_view_detail_price))
            .check(matches(withText(expectedPrice)))

        onView(withId(R.id.image_view_detail_product))
            .check(matches(isDisplayed()))
    }
}
