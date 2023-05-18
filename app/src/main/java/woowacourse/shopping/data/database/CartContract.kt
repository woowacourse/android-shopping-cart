package woowacourse.shopping.data.database

import android.provider.BaseColumns

object CartContract {
    const val CREATE_SQL = "CREATE TABLE IF NOT EXISTS ${Cart.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${Cart.PRODUCT_ID} INTEGER," +
        "${Cart.PRODUCT_COUNT} INTEGER default 0," +
        "${Cart.IS_SELECTED} TEXT default 'n')"

    const val DROP_SQL = "DROP TABLE IF EXISTS ${Cart.TABLE_NAME}"

    fun getCartSql(startPosition: Int, CartItemCount: Int): String {
        return "SELECT * " +
            "FROM ${Cart.TABLE_NAME} " +
            "LIMIT $startPosition, $CartItemCount"
    }

    object Cart : BaseColumns {
        const val TABLE_NAME = "Cart"
        const val PRODUCT_ID = "ProductId"
        const val PRODUCT_COUNT = "ProductCount"
        const val IS_SELECTED = "IsSelected"
    }
}
