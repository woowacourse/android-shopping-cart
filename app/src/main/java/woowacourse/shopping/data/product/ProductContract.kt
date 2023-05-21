package woowacourse.shopping.data.product

import android.provider.BaseColumns

object ProductContract : BaseColumns {
    const val TABLE_NAME = "product"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_IMAGE_URL = "image_url"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_PRICE = "price"
}
