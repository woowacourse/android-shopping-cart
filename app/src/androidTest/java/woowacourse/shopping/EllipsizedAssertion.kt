package woowacourse.shopping

import android.widget.TextView
import androidx.test.espresso.ViewAssertion
import org.junit.Assert.fail // 테스트 실패를 알리기 위해 사용

/**
 * TextView의 텍스트가 마지막 줄에서 말줄임(ellipsized) 되었는지 검증하는 커스텀 ViewAssertion
 */
fun isEllipsized(): ViewAssertion {
    return ViewAssertion { view, noViewFoundException ->
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        if (view !is TextView) {
            fail("Assertion failed: View is not a TextView. Found: ${view?.javaClass?.name}")
        }

        val textView = view as TextView
        textView.layout?.let {
            val lastLine = it.lineCount - 1
            if (lastLine < 0) {
                fail("Assertion failed: TextView has no lines to check for ellipsis. Text: \"${textView.text}\"")
            } else {
                val ellipsisCount = it.getEllipsisCount(lastLine)
                if (ellipsisCount <= 0) {
                    fail("Assertion failed: TextView is not ellipsized Expected ellipsis, Text: \"${textView.text}\"")
                }
            }
        } ?: fail("Assertion failed: TextView layout is null. Cannot check ellipsis state. Text: \"${textView.text}\"")
    }
}
