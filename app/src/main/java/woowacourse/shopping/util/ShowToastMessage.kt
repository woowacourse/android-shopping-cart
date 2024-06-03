package woowacourse.shopping.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToastMessage(
    @StringRes stringId: Int,
) = Toast.makeText(this, this.getString(stringId), Toast.LENGTH_SHORT).show()
