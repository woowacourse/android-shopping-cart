package woowacourse.shopping.shoppingcart

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
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.fixture.createGoods
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity

class ShoppingCartActivityTest {
    private lateinit var scenario: ActivityScenario<ShoppingCartActivity>

    @Before
    fun setUp() {
        ShoppingDataBase.addItem(createGoods())

        val intent = Intent(ApplicationProvider.getApplicationContext(), ShoppingCartActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun `데이터베이스에_저장된_상품_목록이_보인다`() {
        onView(withText("[병천아우내] 모듬순대"))
            .check(matches(isDisplayed()))

        onView(withText("11,900원"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `x_버튼을_누르면_사라진다`() {
        onView(withId(R.id.rv_selected_goods_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.btn_delete))
            .perform(click())

        onView(withText("[병천아우내] 모듬순대"))
            .check(doesNotExist())
    }
}
