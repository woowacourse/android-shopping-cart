package woowacourse.shopping.database

import android.provider.BaseColumns

object ProductContract {
    const val DATABASE_NAME = "product.db"
    const val DATABASE_VERSION = 1

    object CartItemEntry : BaseColumns {
        const val TABLE_NAME = "cart"
        const val COLUMN_NAME_PRODUCT_ID = "product_id"
        const val COLUMN_NAME_ADDED_TIME = "added_time"
        const val COLUMN_NAME_COUNT = "count"
    }

    object RecentlyViewedProductEntry : BaseColumns {
        const val TABLE_NAME = "recently_viewed_product"
        const val COLUMN_NAME_PRODUCT_ID = "product_id"
        const val COLUMN_NAME_VIEWED_TIME = "viewed_time"
    }
}
