package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import org.assertj.core.api.Assertions

val imageUrl = "https://www.naver.com/"
val title = "올리브"
val price = 1500

fun ViewInteraction.hasSizeRecyclerView(expectedItemCount: Int): ViewInteraction {
    val recyclerViewItemCountAssertion =
        ViewAssertion { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            Assertions.assertThat(adapter?.itemCount).isEqualTo(expectedItemCount)
        }
    return check(recyclerViewItemCountAssertion)
}
