package woowacourse.shopping.presentation.shopping.product

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.presentation.shopping.product.adpater.ShoppingViewHolder
import woowacourse.shopping.util.matchDescendantSoftly
import woowacourse.shopping.util.performClickHolderAt
import woowacourse.shopping.util.performScrollToHolder

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
            onView(withId(R.id.rv_product_list))
                .performScrollToHolder<ShoppingViewHolder.LoadMore>()
        // then
        viewInteraction.check(matchDescendantSoftly(plusText))
    }

    @Test
    @DisplayName("더보기 버튼을 누를 시, 상품이 20개 추가 된다")
    fun test2() {
        // given
        val expectProductTitle = "40"
        // when
        onView(withId(R.id.rv_product_list)).performScrollToHolder<ShoppingViewHolder.LoadMore>()
        onView(withId(R.id.rv_product_list)).performClickHolderAt<ShoppingViewHolder.LoadMore>(
            21,
            R.id.btn_load_more_products,
        )
        // 더보기 버튼까지 스크롤
        onView(withId(R.id.rv_product_list))
            .performScrollToHolder<ShoppingViewHolder.LoadMore>()
        // then
        onView(withId(R.id.rv_product_list))
            .check(matchDescendantSoftly(expectProductTitle))
    }
}
