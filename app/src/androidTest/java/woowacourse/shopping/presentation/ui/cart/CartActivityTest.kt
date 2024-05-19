package woowacourse.shopping.presentation.ui.cart

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.ui.testProduct0
import woowacourse.shopping.presentation.ui.testProduct1
import woowacourse.shopping.presentation.ui.testProduct2
import woowacourse.shopping.presentation.ui.testProduct3
import woowacourse.shopping.presentation.ui.testProduct4
import woowacourse.shopping.presentation.ui.testProduct5

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    private lateinit var context: Context
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        cartRepository = CartRepositoryImpl((context as ShoppingApplication).database)
        cartRepository.deleteAll()
    }

    @Test
    fun `화면에_장바구니_아이템이_비어있다면_텅_화면이_보인다`() {
        ActivityScenario.launch(CartActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.tv_empty_cart))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun `화면에_장바구니_아이템이_존재한다면_아이템들이_보인다`() {
        cartRepository.insert(testProduct0, 1)
        cartRepository.insert(testProduct1, 1)
        cartRepository.insert(testProduct2, 1)

        ActivityScenario.launch(CartActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_empty_cart))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun `화면에_장바구니_아이템이_5개가_이하라면_페이지_이동_버튼이_보이지_않는다`() {
        cartRepository.insert(testProduct0, 1)
        cartRepository.insert(testProduct1, 1)
        cartRepository.insert(testProduct2, 1)

        ActivityScenario.launch(CartActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.page_layout))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun `화면에_장바구니_아이템이_5개가_초과한다면_페이지_이동_버튼이_보인다`() {
        cartRepository.insert(testProduct0, 1)
        cartRepository.insert(testProduct1, 1)
        cartRepository.insert(testProduct2, 1)
        cartRepository.insert(testProduct3, 1)
        cartRepository.insert(testProduct4, 1)
        cartRepository.insert(testProduct5, 1)

        ActivityScenario.launch(CartActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.page_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
