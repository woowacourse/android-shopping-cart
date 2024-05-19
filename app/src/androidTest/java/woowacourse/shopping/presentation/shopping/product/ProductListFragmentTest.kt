package woowacourse.shopping.presentation.shopping.product

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepositoryInjector
import woowacourse.shopping.presentation.shopping.product.adpater.ShoppingViewHolder

@RunWith(AndroidJUnit4::class)
class ProductListFragmentTest {
    @Before
    fun setUp() {
        launchFragmentInContainer<ProductListFragment>()
    }

    @Test
    @DisplayName("더보기 버튼이 있다")
    fun test1() {
        // given
        val plusText = "더보기"
        // when
        val viewInteraction =
            onView(withId(R.id.rv_product_list)).perform(
                RecyclerViewActions.scrollToHolder(
                    CoreMatchers.instanceOf(ShoppingViewHolder.LoadMore::class.java),
                ).atPosition(0),
            )
        // then
        viewInteraction.check(matches(hasDescendant(withText(plusText))))
    }

    @Test
    @DisplayName("더보기 버튼을 누를 시, 상품이 20개 추가 된다")
    fun test2() {
        // given
        val plusText = "더보기"
        val expectProductTitle = "21"
        // when
        // 더보기 버튼까지 스크롤
        onView(withId(R.id.rv_product_list)).perform(
            RecyclerViewActions.scrollToHolder(
                CoreMatchers.instanceOf(ShoppingViewHolder.LoadMore::class.java),
            ).atPosition(0),
        )
        // 더보기 버튼 클릭
        onView(withText(containsString(plusText))).perform(click())
        // 더보기 버튼까지 스크롤
        val interaction =
            onView(withId(R.id.rv_product_list)).perform(
                RecyclerViewActions.scrollToHolder(
                    CoreMatchers.instanceOf(ShoppingViewHolder.Product::class.java),
                ).atPosition(20),
            )
        // then
        interaction.check(matches(hasDescendant(withText(containsString(expectProductTitle)))))
    }
}
