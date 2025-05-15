package woowacourse.shopping

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.ext.isDisplayed
import woowacourse.shopping.ext.performClick
import woowacourse.shopping.fixture.fakeContext
import woowacourse.shopping.mathcer.ProductRecyclerViewMatchers
import woowacourse.shopping.view.main.MainActivity

class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        val intent = Intent(fakeContext, MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    @Test
    fun `전달받은_상품_목록을_출력한다`() {
        onView(withText("마리오 그린올리브 300g")).isDisplayed()
        onView(withText("3,980원")).isDisplayed()
        onView(withText("비비고 통새우 만두 200g")).isDisplayed()
        onView(withText("81,980원")).isDisplayed()
    }

    @Test
    fun `상품을_클릭하면_상품_상세_정보가_출력된다`() {
        onView(
            ProductRecyclerViewMatchers.atPositionOnView(
                0,
                R.id.root,
            ),
        ).performClick()

        onView(withText("마리오 그린올리브 300g")).isDisplayed()
        onView(withText("3,980원")).isDisplayed()
    }
}
