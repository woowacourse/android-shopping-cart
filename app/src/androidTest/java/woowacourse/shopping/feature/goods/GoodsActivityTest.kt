package woowacourse.shopping.feature.goods

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity

@Suppress("ktlint:standard:function-naming")
class GoodsActivityTest {
    @Before
    fun setup() {
        ActivityScenario.launch(GoodsActivity::class.java)
        Intents.init()
    }

    @Test
    fun 상품_목록이_표시된다() {
        onView(withId(R.id.rv_goods))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 스크롤_했을_때_더보기_버튼이_표시된다() {
        repeat(5) {
            onView(withId(R.id.rv_goods)).perform(swipeUp())
        }

        onView(withId(R.id.btn_more))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 더보기_버튼을_클릭하면_다음_상품들이_로드된다() {
        var beforeItemCount = 0
        onView(withId(R.id.rv_goods)).check { view, _ ->
            val recyclerView = view as RecyclerView
            beforeItemCount = recyclerView.adapter?.itemCount ?: 0
        }

        repeat(5) {
            onView(withId(R.id.rv_goods)).perform(swipeUp())
        }

        Thread.sleep(1000)
        onView(withId(R.id.btn_more)).perform(click())

        var afterItemCount = 0
        onView(withId(R.id.rv_goods)).check { view, _ ->
            val recyclerView = view as RecyclerView
            afterItemCount = recyclerView.adapter?.itemCount ?: 0
        }

        assertThat(afterItemCount > beforeItemCount).isTrue
    }

    @Test
    fun 상품_아이템을_클릭하면_상품_상세_화면으로_이동한다() {
        onView(withId(R.id.rv_goods))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        intended(hasComponent(GoodsDetailsActivity::class.java.name))
    }

    @Test
    fun 장바구니_아이콘을_클릭하면_장바구니_화면으로_이동한다() {
        onView(withId(R.id.nav_cart)).perform(click())

        intended(hasComponent(CartActivity::class.java.name))
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}
