package woowacourse.shopping.presentation.shoppingcart

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.fixture.createGoods

class ShoppingCartActivityTest {
    private lateinit var scenario: ActivityScenario<ShoppingCartActivity>
    private lateinit var intent: Intent

    @Test
    fun `데이터베이스에_저장된_상품_목록이_보인다`() {
        setUp(1)

        onView(withText("0"))
            .check(matches(isDisplayed()))

        onView(withText("11,900원"))
            .check(matches(isDisplayed()))

        tearDown(1)
    }

    @Test
    fun `x_버튼을_누르면_해당_상품이_사라진다`() {
        // given
        setUp(1)
        onView(withId(R.id.rv_selected_goods_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        // when
        onView(withId(R.id.btn_delete))
            .perform(click())

        // then
        onView(withText("0"))
            .check(doesNotExist())

        tearDown(1)
    }

    @Test
    fun `상품이_5개_이하면_페이지_정보가_보이지_않는다`() {
        // given
        setUp(4)

        // then
        onView(withId(R.id.cl_page_info))
            .check(matches(not(isDisplayed())))

        tearDown(4)
    }

    @Test
    fun `상품이_5개_초과면_페이지_정보가_보인다`() {
        // given
        setUp(6)

        // then
        onView(withId(R.id.cl_page_info))
            .check(matches(isDisplayed()))

        tearDown(6)
    }

    @Test
    fun `다음_페이지_버튼을_누르면_페이지가_증가한다`() {
        // given
        setUp(10)

        onView(withId(R.id.tv_page))
            .check(matches(withText("1")))

        // when
        clickButton(R.id.btn_next_page)

        // then
        onView(withId(R.id.tv_page))
            .check(matches(withText("2")))

        tearDown(10)
    }

    @Test
    fun `이전_페이지_버튼을_누르면_페이지가_감소한다`() {
        // given
        setUp(10)
        clickButton(R.id.btn_next_page)

        onView(withId(R.id.tv_page))
            .check(matches(withText("2")))

        // when
        clickButton(R.id.btn_previous_page)

        // then
        onView(withId(R.id.tv_page))
            .check(matches(withText("1")))

        tearDown(10)
    }

    private fun setUp(count: Int) {
        addItems(count)

        intent = Intent(ApplicationProvider.getApplicationContext(), ShoppingCartActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    private fun tearDown(count: Int) {
        removeItems(count)

        scenario.close()
    }

    private fun addItems(count: Int) {
        repeat(count) {
            ShoppingDataBase.addItem(createGoods("$it"))
        }
    }

    private fun removeItems(count: Int) {
        repeat(count) {
            ShoppingDataBase.removeItem(createGoods("$it"))
        }
    }

    private fun clickButton(button: Int) {
        onView(withId(button))
            .perform(click())
    }
}
