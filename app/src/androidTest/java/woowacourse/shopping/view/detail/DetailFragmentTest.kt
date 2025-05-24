package woowacourse.shopping.view.detail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.R
import woowacourse.shopping.di.provider.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.fixture.FakeCartRepository
import woowacourse.shopping.fixture.FakeProductRepository
import woowacourse.shopping.fixture.FakeRecentProductRepository
import woowacourse.shopping.fixture.productsFixture
import woowacourse.shopping.presentation.view.detail.DetailFragment

class DetailFragmentTest {
    @BeforeEach
    fun setup() {
        val fakeProductRepository = FakeProductRepository()
        val fakeCartRepository =
            FakeCartRepository(
                initialCartItems = productsFixture.take(1).map { CartItem(it, 1) },
            )
        val fakeRecentProductRepository = FakeRecentProductRepository()

        RepositoryProvider.initProductRepository(fakeProductRepository)
        RepositoryProvider.initCartRepository(fakeCartRepository)
        RepositoryProvider.initRecentProductRepository(fakeRecentProductRepository)

        launchFragmentInContainer(
            DetailFragment.newBundle(productsFixture[0].id),
            themeResId = R.style.Theme_Shopping,
        ) { DetailFragment() }
    }

    @Test
    fun `상품의_이름_가격_이미지를_확인할_수_있다`() {
        onView(withId(R.id.text_view_detail_product_name)).check(
            matches(
                withText(
                    productsFixture[0].name,
                ),
            ),
        )

        val expectedPrice = "%,d원".format(productsFixture[0].price.value)

        onView(withId(R.id.text_view_detail_price)).check(matches(withText(expectedPrice)))
        onView(withId(R.id.image_view_detail_product)).check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니에_추가할_상품_개수를_증가시킬_수_있다`() {
        onView(withId(R.id.btn_quantity_plus)).perform(click())
        onView(withId(R.id.textview_quantity)).check(matches(withText("2")))

        val expectedPrice = "%,d원".format(productsFixture[0].price.value * 2)
        onView(withId(R.id.text_view_detail_price)).check(matches(withText(expectedPrice)))
    }

    @Test
    fun `장바구니에_추가할_상품_개수를_감소시킬_수_있다`() {
        // Give
        onView(withId(R.id.btn_quantity_plus)).perform(click())
        onView(withId(R.id.btn_quantity_plus)).perform(click())
        onView(withId(R.id.textview_quantity)).check(matches(withText("3")))

        // When
        onView(withId(R.id.btn_quantity_minus)).perform(click())

        // Then
        onView(withId(R.id.textview_quantity)).check(matches(withText("2")))
        val expectedPrice = "%,d원".format(productsFixture[0].price.value * 2)
        onView(withId(R.id.text_view_detail_price)).check(matches(withText(expectedPrice)))
    }

    @Test
    fun `장바구니에_추가할_상품_개수가_1개인_경우_감소시킬_수_없다`() {
        // Give
        onView(withId(R.id.textview_quantity)).check(matches(withText("1")))

        // When
        onView(withId(R.id.btn_quantity_minus)).perform(click())

        // Then
        onView(withId(R.id.textview_quantity)).check(matches(withText("1")))
    }
}
