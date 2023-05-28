package woowacourse.shopping.data.database

import android.provider.BaseColumns

object RecentProductContract {
    const val CREATE_SQL = "CREATE TABLE IF NOT EXISTS ${RecentProduct.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${RecentProduct.PRODUCT_ID} INTEGER UNIQUE," +
        "${RecentProduct.CREATE_DATE} TEXT)"

    const val DROP_SQL = "DROP TABLE IF EXISTS ${RecentProduct.TABLE_NAME}"

    object RecentProduct : BaseColumns {
        const val TABLE_NAME = "RecentProduct"
        const val PRODUCT_ID = "ProductId"
        const val CREATE_DATE = "CreateDate"
    }
}
