package woowacourse.shopping.data.database

import android.provider.BaseColumns

object CartContract {
    const val CREATE_SQL = "CREATE TABLE IF NOT EXISTS ${Cart.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${Cart.PRODUCT_ID} INTEGER)"

    const val DROP_SQL = "DROP TABLE IF EXISTS ${Cart.TABLE_NAME}"

    fun getCartSql(startPosition: Int, CartItemCount: Int): String {
        return "SELECT count(*) as ProductCount,${Cart.PRODUCT_ID} " +
                "FROM ${Cart.TABLE_NAME} " +
                "GROUP BY ${Cart.PRODUCT_ID} " +
                "LIMIT $startPosition, $CartItemCount"
    }

    object Cart : BaseColumns {
        const val TABLE_NAME = "Cart"
        const val PRODUCT_ID = "ProductId"
        const val PRODUCT_COUNT = "ProductCount"
    }
}
