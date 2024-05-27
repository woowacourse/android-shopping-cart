package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.junit5.MockKExtension
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_1
import woowacourse.shopping.data.repsoitory.DummyOrder
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.presentation.ui.utils.RecyclerViewItemCountAssertion

@RunWith(AndroidJUnit4::class)
@ExtendWith(MockKExtension::class)
class ShoppingCartActivityTest {
    private val repository: OrderRepository = DummyOrder

    @Before
    fun setUp() {
        repository.removeAllOrder()
    }

    @Test
    fun `4개의_상품이_장바구니에_있을_때_페이지_이동_버튼이_안_보인다`() {
        // Given
        repeat(4) {
            repository.plusOrder(product = STUB_PRODUCT_1)
        }

        ActivityScenario.launch(ShoppingCartActivity::class.java)

        onView(withId(R.id.tv_next_page))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_pre_page))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun `5개의_상품이_장바구니에_있을_때_페이지_이동_버튼이_보인다`() {
        // Given
        repeat(5) {
            repository.plusOrder(product = STUB_PRODUCT_1)
        }

        ActivityScenario.launch(ShoppingCartActivity::class.java)

        onView(withId(R.id.tv_next_page))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_pre_page))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니의_상품이_6개_이상일_때_이전_페이지_버튼이_비활성화_된다`() {
        repeat(6) {
            repository.plusOrder(product = STUB_PRODUCT_1)
        }

        ActivityScenario.launch(ShoppingCartActivity::class.java)

        onView(withId(R.id.tv_pre_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun `장바구니의_상품이_6개_이상일_때_다음_페이지_버튼이_활성화_된다`() {
        repeat(6) {
            repository.plusOrder(product = STUB_PRODUCT_1)
        }

        ActivityScenario.launch(ShoppingCartActivity::class.java)

        onView(withId(R.id.tv_next_page))
            .check(matches(isEnabled()))
    }

    @Test
    fun `장바구니의_상품이_6개_이상일_때_다음_페이지로_이동하면_하나의_상품이_보인다`() {
        repeat(6) {
            repository.plusOrder(product = STUB_PRODUCT_1)
        }

        ActivityScenario.launch(ShoppingCartActivity::class.java)
        onView(withId(R.id.tv_next_page))
            .perform(click())

        onView(withId(R.id.rv_order_list))
            .check(RecyclerViewItemCountAssertion(1))
    }

    @Test
    fun `장바구니에_10개의_상품이_있을_때_다음_페이지로_이동하면_다음_페이지_버튼이_비활성화_된다`() {
        repeat(10) {
            repository.plusOrder(product = STUB_PRODUCT_1)
        }

        ActivityScenario.launch(ShoppingCartActivity::class.java)
        onView(withId(R.id.tv_next_page))
            .perform(click())

        onView(withId(R.id.tv_next_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun `장바구니에_10개의_상품이_있을_때_다음_페이지로_이동하면_이전_페이지_버튼이_활성화_된다`() {
        repeat(10) {
            repository.plusOrder(product = STUB_PRODUCT_1)
        }

        ActivityScenario.launch(ShoppingCartActivity::class.java)
        onView(withId(R.id.tv_next_page))
            .perform(click())

        onView(withId(R.id.tv_pre_page))
            .check(matches(isEnabled()))
    }

    @Test
    fun `장바구니의_상품이_있을_때_엑스_버튼을_누르면_장바구니_리스트에서_없어진다`() {
        repository.plusOrder(product = STUB_PRODUCT_1)

        ActivityScenario.launch(ShoppingCartActivity::class.java)
        onView(withId(R.id.iv_closed))
            .perform(click())

        onView(withId(R.id.rv_order_list))
            .check(RecyclerViewItemCountAssertion(0))
    }
}
