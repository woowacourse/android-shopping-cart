package woowacourse.shopping

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity

@Suppress("ktlint:standard:function-naming")
class GoodsDetailsActivityTest {
    private lateinit var scenario: ActivityScenario<GoodsDetailsActivity>

    private val testGoods =
        GoodsUiModel(
            name = "테스트 상품",
            price = 10000,
            thumbnailUrl = "",
        )

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = GoodsDetailsActivity.newIntent(context, testGoods)
        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun 상품_이름이_화면에_보인다() {
        onView(withId(R.id.tv_goods_details_name))
            .check(matches(withText("테스트 상품")))
    }

    @Test
    fun 상품_가격이_화면에_보인다() {
        onView(withId(R.id.tv_goods_details_price))
            .check(matches(withText("10,000원")))
    }

    @Test
    fun 닫기_메뉴_클릭시_액티비티가_종료된다() {
        onView(withId(R.id.nav_close)).perform(click())

        scenario.onActivity { activity ->
            assert(activity.isFinishing || activity.isDestroyed)
        }
    }
}
