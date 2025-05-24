package woowacourse.shopping

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.ext.isTextMatches
import woowacourse.shopping.ext.performClick
import woowacourse.shopping.fixture.fakeContext
import woowacourse.shopping.view.detail.DetailActivity

class DetailActivityTest {
    private lateinit var scenario: ActivityScenario<DetailActivity>

    @Before
    fun setUp() {
        val intent = DetailActivity.newIntent(fakeContext, 2L)
        scenario = ActivityScenario.launch(intent)
    }

    @Test
    fun `상품_상세_화면이_정상적으로_표시된다`() {
        onView(withId(R.id.text_view_product_name)).isTextMatches("[태우한우] 1+ 한우 안심 스테이크 200g (냉장)")
        onView(withId(R.id.text_view_product_price)).isTextMatches("2,000원")
    }

    @Test
    fun `수량을_증가시키면_가격이_증가한다`() {
        onView(withId(R.id.image_view_plus)).performClick()

        onView(withId(R.id.text_view_product_price)).isTextMatches("4,000원")
    }

    @Test
    fun `수량을_감소시키면_가격이_감소한다`() {
        onView(withId(R.id.text_view_product_price)).isTextMatches("2,000원")

        onView(withId(R.id.image_view_plus)).performClick()
        onView(withId(R.id.image_view_minus)).performClick()

        onView(withId(R.id.text_view_product_price)).isTextMatches("2,000원")
    }
}
