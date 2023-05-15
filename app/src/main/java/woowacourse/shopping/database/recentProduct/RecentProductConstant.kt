package woowacourse.shopping.database.recentProduct

import android.provider.BaseColumns

object RecentProductConstant : BaseColumns {
    const val TABLE_NAME = "recent_product"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_PRICE = "price"
    const val TABLE_COLUMN_IMAGE_URL = "image_url"
    const val TABLE_COLUMN_SAVE_TIME = "time"
}
