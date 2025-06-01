package woowacourse.shopping

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.ext.isDisplayed
import woowacourse.shopping.ext.isTextMatches
import woowacourse.shopping.ext.isVisibilityGone
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
        onView(withText("[태우한우] 1+ 한우 안심 스테이크 200g (냉장)")).isDisplayed()
        onView(withText("2,000원")).isDisplayed()
    }

    @Test
    fun `수량_토클_버튼의_수량이_0개가_되면_수량_토글_버튼이_사라진다`() {
        onView(ProductRecyclerViewMatchers.atPositionOnView(4, R.id.button_quantity)).performClick()
        onView(ProductRecyclerViewMatchers.atPositionOnView(4, R.id.image_view_minus)).performClick()

        onView(ProductRecyclerViewMatchers.atPositionOnView(4, R.id.tv_cart_count)).isVisibilityGone()
    }

    @Test
    fun `상품을_클릭하면_상품_상세_정보가_출력된다`() {
        onView(ProductRecyclerViewMatchers.atPositionOnView(0, R.id.root)).performClick()

        onView(withText("마리오 그린올리브 300g")).isDisplayed()
        onView(withText("3,980원")).isDisplayed()
    }

    @Test
    fun `상품_수량_버튼을_클릭하면_상품_수량_증감_버튼이_출력된다`() {
        onView(ProductRecyclerViewMatchers.atPositionOnView(5, R.id.button_quantity)).performClick()
        onView(ProductRecyclerViewMatchers.atPositionOnView(5, R.id.tv_cart_count)).isTextMatches("1")
    }

    @Test
    fun `장바구니에_추가되지_않은_상품의_수량_버튼을_누르면_수량_토글_박스가_출력되고_상품이_1개_추가된다`() {
        onView(ProductRecyclerViewMatchers.atPositionOnView(3, R.id.button_quantity)).performClick()

        onView(ProductRecyclerViewMatchers.atPositionOnView(3, R.id.tv_cart_count)).isTextMatches("1")
    }

    @Test
    fun `상품의_장바구니에_담긴_수량을_증가하고_감소시킨다`() {
        onView(ProductRecyclerViewMatchers.atPositionOnView(2, R.id.button_quantity)).performClick()
        onView(ProductRecyclerViewMatchers.atPositionOnView(2, R.id.image_view_plus)).performClick()

        onView(ProductRecyclerViewMatchers.atPositionOnView(2, R.id.tv_cart_count)).isTextMatches("2")

        onView(ProductRecyclerViewMatchers.atPositionOnView(2, R.id.image_view_minus)).performClick()
        onView(ProductRecyclerViewMatchers.atPositionOnView(2, R.id.tv_cart_count)).isTextMatches("1")

        onView(ProductRecyclerViewMatchers.atPositionOnView(2, R.id.tv_cart_count)).isDisplayed()
    }

    @Test
    fun `상품의_수량이_증가하면_장바구니_버튼_뱃지의_수량도_증가한다`() {
        onView(ProductRecyclerViewMatchers.atPositionOnView(1, R.id.button_quantity)).performClick()
        onView(withId(R.id.text_view_cart)).isTextMatches("1")

        for (i in 2..10) {
            onView(ProductRecyclerViewMatchers.atPositionOnView(1, R.id.image_view_plus)).performClick()

            onView(withId(R.id.text_view_cart)).isTextMatches("$i")
        }
    }

    @After
    fun tearDown() {
        scenario.close()
    }
}
