package woowacourse.shopping.presentation.goods.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.fixture.createGoods
import woowacourse.shopping.presentation.model.toUiModel

class GoodsDetailActivityTest {
    private lateinit var scenario: ActivityScenario<GoodsDetailActivity>

    @Before
    fun setUp() {
        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                GoodsDetailActivity::class.java,
            ).apply {
                putExtra("goods", createGoods().toUiModel())
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
}
