package woowacourse.shopping.data.database.cart

import android.provider.BaseColumns

object CartContract : BaseColumns {
    const val TABLE_NAME = "cart"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_IMAGE_URL = "image_url"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_PRICE = "price"
    const val TABLE_COLUMN_COUNT = "count"
}
