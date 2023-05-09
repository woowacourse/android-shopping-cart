package woowacourse.shopping.database

import android.provider.BaseColumns

object ProductContract {
    const val DATABASE_NAME = "product.db"
    const val DATABASE_VERSION = 1

    object ProductEntry : BaseColumns {
        const val TABLE_NAME = "product"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_IMAGE_URL = "image_url"
    }

    object CartEntry : BaseColumns {
        const val TABLE_NAME = "cart"
        const val COLUMN_NAME_PRODUCT_ID = "product_id"
    }

    object RecentlyViewedProductEntry : BaseColumns {
        const val TABLE_NAME = "recently_viewed_product"
        const val COLUMN_NAME_PRODUCT_ID = "product_id"
    }
}
