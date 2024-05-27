package woowacourse.shopping.view.detail

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.db.recent.RecentProductRepository
import woowacourse.shopping.data.db.recent.RecentProductRepositoryImpl
import woowacourse.shopping.helper.testProduct0
import woowacourse.shopping.helper.testProduct1

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
    private lateinit var recentProductRepository: RecentProductRepository

    @Before
    fun setUp() {
        recentProductRepository = RecentProductRepositoryImpl(ShoppingApplication.recentProductDatabase)
        recentProductRepository.deleteAll()
    }

    private val intent =
        DetailActivity.createIntent(ApplicationProvider.getApplicationContext(), 0L)

    @get:Rule
    val activityRule = ActivityScenarioRule<DetailActivity>(intent)

    @Test
    fun `선택한_상품의_사진이_표시된다`() {
        onView(withId(R.id.iv_detail_product_image))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `선택한_상품의_제목이_표시된다`() {
        onView(withId(R.id.tv_detail_product_name))
            .check(matches(withText("1 대전 장인약과")))
    }

    @Test
    fun `선택한_상품의_가격이_표시된다`() {
        onView(withId(R.id.tv_detail_price))
            .check(matches(withText("10,000원")))
    }

    @Test
    fun `장바구니_담기_버튼을_클릭하면_장바구니_페이지로_이동한다`() {
        onView(withId((R.id.btn_put_cart))).perform(click())
        onView(withId(R.id.activity_cart)).check(matches(isDisplayed()))
    }

    @Test
    fun `X_버튼을_클릭하면_상품_상세_페이지가_종료된다`() {
        onView(withId((R.id.btn_finish))).perform(click())
        activityRule.scenario.onActivity { activity ->
            assert(activity.isFinishing)
        }
    }

    @Test
    fun `마지막으로_본_상품이_나타난다`() {
        recentProductRepository.save(testProduct0)
        recentProductRepository.save(testProduct1)

        ActivityScenario.launch<DetailActivity>(intent)

        onView(withId(R.id.cl_recent_viewed_products))
            .check(matches(isDisplayed()))
    }
}
