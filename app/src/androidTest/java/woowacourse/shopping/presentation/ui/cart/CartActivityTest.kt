package woowacourse.shopping.presentation.ui.cart

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.presentation.ui.dummyProduct

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    @Test
    fun `장바구니에_아이템_리스트가_보여진다`() {
        ActivityScenario.launch(CartActivity::class.java)
        onView(ViewMatchers.withId(R.id.rv_carts))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `상품이_5개_이하면_페이지를_이동하는_버튼이_안보인다`() {
        repeat(5) { index ->
            DummyCartRepository.addData(dummyProduct.copy(id = index.toLong()))
        }
        ActivityScenario.launch(CartActivity::class.java)
        onView(ViewMatchers.withId(R.id.layout_page))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun `상품이_5개_초과면_페이지를_이동하는_버튼이_보여진다`() {
        repeat(6) { index ->
            DummyCartRepository.addData(dummyProduct.copy(id = index.toLong()))
        }
        ActivityScenario.launch(CartActivity::class.java)
        onView(ViewMatchers.withId(R.id.layout_page))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
