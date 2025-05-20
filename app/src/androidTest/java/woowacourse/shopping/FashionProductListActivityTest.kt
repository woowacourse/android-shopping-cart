package woowacourse.shopping

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.ui.fashionlist.FashionProductListActivity

class FashionProductListActivityTest {
    private var viewHolder: RecyclerView.ViewHolder? = null

    @BeforeEach
    fun setUp() {
        val scenario = ActivityScenario.launch(FashionProductListActivity::class.java)

        scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.products_recyclerView)
            viewHolder = recyclerView.findViewHolderForAdapterPosition(0)
        }
    }

    @Test
    fun 장바구니_메뉴가_보인다() {
        onView(withId(R.id.menu_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 상품_목록에_상품명이_일치한다() {
        val title = viewHolder?.itemView?.findViewById<TextView>(R.id.tv_product_title)
        assertThat(title?.text).isEqualTo("오버사이즈 칼라드 스웨트셔츠 [블랙]")
    }

    @Test
    fun 상품_목록에_상품_가격이_일치한다() {
        val title = viewHolder?.itemView?.findViewById<TextView>(R.id.tv_product_price)
        assertThat(title?.text).isEqualTo("35,090원")
    }
}
