package woowacourse.shopping.util

import android.app.Activity
import android.widget.Toast

private const val FAILED_TO_RECEIVING_DATA = "데이터를 불러오지 못했습니다."

fun Activity.handleMissingSerializableData() {
    Toast.makeText(this, FAILED_TO_RECEIVING_DATA, Toast.LENGTH_SHORT).show()
    finish()
}
