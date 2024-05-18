import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import woowacourse.shopping.presentation.ui.shopping.ShoppingActivity

@RunWith(AndroidJUnit4::class)
class ShoppingActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<ShoppingActivity> =
        ActivityScenarioRule<ShoppingActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ShoppingActivity::class.java,
            ),
        )

    /*@Test
    fun `20개의_상품_목록이_있을_때_20개의_상품_목록으로_스크롤을_하면_더보기_버튼이_보인다`() {
        // Given
        val recyclerView = onView(withId(R.id.rv_product_list))

        // When
        recyclerView.perform(scrollToPosition<ShoppingViewHolder>(10))

        // then
        onView(withId(R.id.btn_load_more)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)),
        )
    }*/
}
