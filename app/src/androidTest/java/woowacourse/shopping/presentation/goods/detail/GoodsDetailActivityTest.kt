package woowacourse.shopping.presentation.goods.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.fixture.createGoods

class GoodsDetailActivityTest {
    private lateinit var scenario: ActivityScenario<GoodsDetailActivity>

    @Before
    fun setUp() {
        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                GoodsDetailActivity::class.java,
            ).apply {
                putExtra("goods", createGoods())
            }

        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun `상품의_이름이_보인다`() {
        onView(withId(R.id.tv_goods_name))
            .check(matches(withText("[병천아우내] 모듬순대")))
    }

    @Test
    fun `상품의_가격이_보인다`() {
        onView(withId(R.id.tv_goods_price))
            .check(matches(withText("11,900원")))
    }

    @Test
    fun `plus_버튼을_누르면_개수가_증가한다`() {
        // when
        onView(withId(R.id.tv_plus))
            .perform(click())

        // then
        onView(withId(R.id.tv_count))
            .check(matches(withText("2")))
    }

    @Test
    fun `minus_버튼을_누르면_개수가_증가한다`() {
        // given
        onView(withId(R.id.tv_plus))
            .perform(click())

        // when
        onView(withId(R.id.tv_minus))
            .perform(click())

        // then
        onView(withId(R.id.tv_count))
            .check(matches(withText("1")))
    }
}
