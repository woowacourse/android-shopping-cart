package woowacourse.shopping.util.extension

import android.app.Activity
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> T.setContentView(activity: Activity): T = run {
    activity.setContentView(root)
    this
}
