package woowacourse.shopping

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.presentation.goods.list.GoodsActivity
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity

class GoodsActivityTest {
    private lateinit var scenario: ActivityScenario<GoodsActivity>

    @Before
    fun setUp() {
        Intents.init()
        val intent = Intent(ApplicationProvider.getApplicationContext(), GoodsActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        Intents.release()
        scenario.close()
    }

    @Test
    fun `상품의_이름이_보인다`() {
        onView(withId(R.id.rv_goods_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withText("[병천아우내] 모듬순대"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상품의_가격이_보인다`() {
        onView(withId(R.id.rv_goods_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withText("11,900원"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니_버튼을_클릭하면_장바구니_목록_화면으로_이동한다`() {
        onView(withId(R.id.action_cart))
            .perform(click())

        intended(hasComponent(ShoppingCartActivity::class.java.name))
    }
}
