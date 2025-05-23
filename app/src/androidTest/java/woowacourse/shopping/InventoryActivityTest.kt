
package woowacourse.shopping

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.matcher.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.shopping.matcher.isDisplayed
import woowacourse.shopping.matcher.isEllipsized
import woowacourse.shopping.matcher.matchSizeWithViewType
import woowacourse.shopping.matcher.matchText
import woowacourse.shopping.matcher.performClick
import woowacourse.shopping.matcher.scrollToPosition
import woowacourse.shopping.matcher.sizeGreaterThan
import woowacourse.shopping.view.inventory.InventoryActivity

@Suppress("FunctionName")
class InventoryActivityTest {
    @get:Rule
    val inventoryActivityScenarioRule = ActivityScenarioRule(InventoryActivity::class.java)

    @Test
    fun 상품의_목록이_표시된다() {
        onView(
            withRecyclerView(R.id.rv_product_list).atPositionOnView(
                0,
                R.id.tv_product_name,
            ),
        ).matchText("[병천아우내] 모듬순대")
        onView(
            withRecyclerView(R.id.rv_product_list).atPositionOnView(
                0,
                R.id.tv_product_price,
            ),
        ).matchText("11,900원")
        onView(
            withRecyclerView(R.id.rv_product_list).atPositionOnView(
                0,
                R.id.iv_product_image,
            ),
        ).isDisplayed()
    }

    @Test
    fun 상품의_목록은_20개_단위로_표시된다() {
        onView(withId(R.id.rv_product_list)).check(matchSizeWithViewType(20, R.layout.item_inventory_product))
    }

    @Test
    fun 더보기_버튼을_눌러서_상품을_추가_로드할_수_있다() {
        onView(withId(R.id.rv_product_list)).perform(scrollToPosition(20))
        onView(withId(R.id.btn_show_more)).performClick()
        onView(withId(R.id.rv_product_list)).check(sizeGreaterThan(20))
    }

    @Test
    fun 상품의_이름이_너무_길_경우_말줄임표로_표시된다() {
        onView(
            withRecyclerView(R.id.rv_product_list).atPositionOnView(
                1,
                R.id.tv_product_name,
            ),
        ).check(isEllipsized())
    }

    @Test
    fun 상품을_클릭하면_상품_상세_화면으로_이동된다() {
        onView(
            withRecyclerView(R.id.rv_product_list).atPositionOnView(
                0,
                R.id.tv_product_name,
            ),
        ).performClick()
        onView(withId(R.id.product_detail)).isDisplayed()
    }

    @Test
    fun 장바구니_아이콘을_클릭하면_장바구니_화면으로_이동된다() {
        onView(withId(R.id.menu_item_shopping_cart)).performClick()
        onView(withId(R.id.rv_shopping_cart_list)).isDisplayed()
    }
}
