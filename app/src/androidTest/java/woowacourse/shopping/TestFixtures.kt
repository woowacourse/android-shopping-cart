package woowacourse.shopping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.assertj.core.api.Assertions

val imageUrl = "https://www.naver.com/"
val title = "올리브"
val price = 1500

class RecyclerViewItemCountAssertion(private val expectedItemCount: Int) : ViewAssertion {
    override fun check(
        view: View?,
        noViewFoundException: NoMatchingViewException?,
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        Assertions.assertThat(adapter?.itemCount).isEqualTo(expectedItemCount)
    }
}
