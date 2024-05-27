package woowacourse.shopping.view.cart

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication.Companion.cartDatabase
import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.data.db.cart.CartRepositoryImpl
import woowacourse.shopping.helper.testProduct0
import woowacourse.shopping.helper.testProduct1
import woowacourse.shopping.helper.testProduct2
import woowacourse.shopping.helper.testProduct3
import woowacourse.shopping.helper.testProduct4
import woowacourse.shopping.helper.testProduct5

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = CartRepositoryImpl(cartDatabase)
        cartRepository.deleteAll()
    }

    @After
    fun tearDown() {
        cartRepository.deleteAll()
    }

    @Test
    fun `화면에_장바구니_아이템이_비어있다면_텅_화면이_보인다`() {
        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.tv_empty_cart))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_cart))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun `화면에_장바구니_아이템이_존재한다면_아이템들이_보인다`() {
        cartRepository.save(testProduct0, 1)
        cartRepository.save(testProduct1, 1)
        cartRepository.save(testProduct2, 1)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.rv_cart))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_empty_cart))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun `화면에_장바구니_아이템이_5개가_이하라면_페이지_이동_버튼이_보이지_않는다`() {
        cartRepository.save(testProduct0, 1)
        cartRepository.save(testProduct1, 1)
        cartRepository.save(testProduct2, 1)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.page_layout))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun `화면에_장바구니_아이템이_5개가_초과한다면_페이지_이동_버튼이_보인다`() {
        cartRepository.save(testProduct0, 1)
        cartRepository.save(testProduct1, 1)
        cartRepository.save(testProduct2, 1)
        cartRepository.save(testProduct3, 1)
        cartRepository.save(testProduct4, 1)
        cartRepository.save(testProduct5, 1)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.page_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `화면에_수량_조절_버튼이_보인다`() {
        cartRepository.save(testProduct0, 1)
        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_quantity_control))
            .check(matches(isDisplayed()))
    }
}
