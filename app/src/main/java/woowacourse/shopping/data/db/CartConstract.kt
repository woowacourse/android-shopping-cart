package woowacourse.shopping.data.db

import android.provider.BaseColumns

object CartConstract : BaseColumns {
    const val TABLE_NAME = "cart"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_COUNT = "count"
}
