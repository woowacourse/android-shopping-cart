package woowacourse.shopping.util.extension

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes

inline fun View.setOnSingleClickListener(
    delay: Long = 500L,
    crossinline block: (View) -> Unit,
) {
    var previousClickedTime = 0L
    setOnClickListener { view ->
        val clickedTime = System.currentTimeMillis()
        if (clickedTime - previousClickedTime >= delay) {
            block(view)
            previousClickedTime = clickedTime
        }
    }
}

fun View.findTextView(@IdRes id: Int): TextView? = findViewById(id) ?: null

