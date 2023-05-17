package woowacourse.shopping.data.cart

import android.provider.BaseColumns

object CartContract : BaseColumns {
    const val TABLE_NAME = "cart"
    const val TABLE_COLUMN_PRODUCT_ID = "product_id"
    const val TABLE_COLUMN_PRODUCT_IMAGE_URL = "product_image_url"
    const val TABLE_COLUMN_PRODUCT_NAME = "product_name"
    const val TABLE_COLUMN_PRODUCT_PRICE = "product_price"
    const val TABLE_COLUMN_COUNT = "count"
    const val TABLE_COLUMN_CHECKED = "checked"
}
