package woowacourse.shopping.presentation.ui.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            DetailActivity::class.java,
        ).apply {
            putExtra(DetailActivity.PRODUCT_ID, 0L)
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<DetailActivity>(intent)

    @Test
    fun `상품_상세_페이지가_표시된다`() {
        onView(withId(R.id.activity_detail))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `선택한_상품의_사진이_표시된다`() {
        onView(withId(R.id.iv_detail_product_image))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `선택한_상품의_제목이_표시된다`() {
        onView(withId(R.id.tv_detail_product_name))
            .check(matches(withText("1 [든든] 동원 스위트콘")))
    }

    @Test
    fun `선택한_상품의_가격이_표시된다`() {
        onView(withId(R.id.tv_detail_price))
            .check(matches(withText("99,800원")))
    }
}
