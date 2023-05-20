package woowacourse.shopping.data.database

import android.provider.BaseColumns

object CartContract {
    const val CREATE_SQL = "CREATE TABLE IF NOT EXISTS ${Cart.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${Cart.PRODUCT_ID} INTEGER," +
        "${Cart.COUNT} INTEGER," +
        "${Cart.CHECKED} INTEGER)"

    const val DROP_SQL = "DROP TABLE IF EXISTS ${Cart.TABLE_NAME}"

    object Cart : BaseColumns {
        const val TABLE_NAME = "Cart"
        const val PRODUCT_ID = "ProductId"
        const val COUNT = "Count"
        const val CHECKED = "Checked"
    }
}
