package woowacourse.shopping.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.matcher.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.shopping.matcher.isDisplayed
import woowacourse.shopping.matcher.isEllipsized
import woowacourse.shopping.matcher.matchSize
import woowacourse.shopping.matcher.matchText
import woowacourse.shopping.matcher.performClick
import woowacourse.shopping.matcher.scrollToPosition
import woowacourse.shopping.matcher.sizeGreaterThan
import woowacourse.shopping.view.main.MainActivity

@Suppress("FunctionName")
class MainActivityTest {
    @get:Rule
    val mainActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 상품의_목록이_표시된다() {
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.tv_product_name,
            ),
        ).matchText("[병천아우내] 모듬순대")
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.tv_product_price,
            ),
        ).matchText("11,900원")
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.iv_product_image,
            ),
        ).isDisplayed()
    }

    @Test
    fun 상품의_목록은_20개_단위로_표시된다() {
        onView(withId(R.id.product_list)).check(matchSize(21))
    }

    @Test
    fun 더보기_버튼을_눌러서_상품을_추가_로드할_수_있다() {
        onView(withId(R.id.product_list)).perform(scrollToPosition(19))
        Thread.sleep(1000)
        onView(withId(R.id.btn_load_more_products)).performClick()
        onView(withId(R.id.product_list)).check(sizeGreaterThan(20))
    }

    @Test
    fun 상품의_이름이_너무_길_경우_말줄임표로_표시된다() {
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                2,
                R.id.tv_product_name,
            ),
        ).check(isEllipsized())
    }

    @Test
    fun 상품을_클릭하면_상품_상세_화면으로_이동된다() {
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.tv_product_name,
            ),
        ).performClick()
        onView(withId(R.id.product_detail)).isDisplayed()
    }

    @Test
    fun 장바구니_아이콘을_클릭하면_장바구니_화면으로_이동된다() {
        onView(withId(R.id.menu_item_shopping_cart)).performClick()
        onView(withId(R.id.shopping_cart_list)).isDisplayed()
    }

    @Test
    fun 상품_목록에서_플러스_버튼_클릭_시_뱃지_카운트가_증가한다() {
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.button_plus,
            ),
        )
            .performClick()

        Thread.sleep(500)

        onView(withId(R.id.shopping_cart_alarm_badge)).isDisplayed()
        onView(withId(R.id.shopping_cart_alarm_badge)).matchText("13")
    }

    @Test
    fun 상품_목록에서_플러스_버튼_클릭_시_상품_수량이_증가한다() {
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.button_plus,
            ),
        )
            .performClick()

        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.text_quantity,
            ),
        )
            .matchText("2")
    }

    @Test
    fun 상품_목록에서_마이너스_버튼_클릭_시_상품_수량이_감소한다() {
        // given
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.button_plus,
            ),
        )
            .performClick()

        // when
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.button_minus,
            ),
        )
            .performClick()

        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.text_quantity,
            ),
        )
            .matchText("1")
    }

    @Test
    fun 상품_목록에서_수량이_0일_경우_수량_선택_버튼이_아닌_플러스_버튼만_제공된다() {
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.button_minus,
            ),
        )
            .performClick()

        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.btn_item_product_add_to_cart,
            ),
        )
            .isDisplayed()
    }

    @Test
    fun 최근_본_상품의_목록이_나타난다() {
        // given
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.iv_product_image,
            ),
        )
            .performClick()
        pressBack()

        onView(
            withRecyclerView(R.id.recent_product_list).atPositionOnView(
                0,
                R.id.tv_product_name,
            ),
        )
            .isDisplayed()
            .matchText("[병천아우내] 모듬순대")
    }

    @Test
    fun 최근_본_상품_목록이_10개가_넘어가면_맨_나중에_있는_아이템이_삭제된다() {
        // given - when
        for (i in 1..11) {
            onView(withId(R.id.product_list))
                .perform(scrollToPosition(i))

            onView(
                withRecyclerView(R.id.product_list)
                    .atPositionOnView(i, R.id.iv_product_image),
            )
                .performClick()
            pressBack()
        }

        Thread.sleep(500)

        // then
        onView(withId(R.id.recent_product_list))
            .perform(scrollToPosition(9))
            .check(matchSize(10))

        onView(
            withRecyclerView(R.id.recent_product_list)
                .atPositionOnView(9, R.id.tv_product_name),
        ).check(matches(not(withText("[병천아우내] 모듬순대"))))
    }

    @Test
    fun 최근_본_상품에_있는_상품을_클릭하면_맨_앞으로_아이템이_이동한다() {
        // given
        for (i in 1..5) {
            onView(withId(R.id.product_list))
                .perform(scrollToPosition(i))

            onView(
                withRecyclerView(R.id.product_list)
                    .atPositionOnView(i, R.id.iv_product_image),
            )
                .performClick()
            pressBack()
        }

        // when
        onView(
            withRecyclerView(R.id.product_list).atPositionOnView(
                1,
                R.id.iv_product_image,
            ),
        )
            .performClick()
        pressBack()

        // then
        onView(
            withRecyclerView(R.id.recent_product_list).atPositionOnView(
                0,
                R.id.tv_product_name,
            ),
        )
            .isDisplayed()
            .matchText("[병천아우내] 모듬순대")
    }
}
