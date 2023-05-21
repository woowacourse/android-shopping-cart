package woowacourse.shopping.data.local.database

import android.provider.BaseColumns

object CartContract {
    const val CREATE_SQL = "CREATE TABLE IF NOT EXISTS ${Cart.TABLE_NAME} (" +
        "${Cart.PRODUCT_ID} INTEGER PRIMARY KEY," +
        "${Cart.PRODUCT_COUNT} INTEGER default 0," +
        "${Cart.IS_SELECTED} TEXT default 'n')"

    const val DROP_SQL = "DROP TABLE IF EXISTS ${Cart.TABLE_NAME}"

    fun getUpdateCartSql(): String {
        return "UPDATE OR REPLACE INTO ${Cart.TABLE_NAME} " +
            "SET ${Cart.PRODUCT_COUNT} = ? " +
            "WHERE ${Cart.PRODUCT_ID} = ?"
    }

    fun getCartSql(startPosition: Int, CartItemCount: Int): String {
        return "SELECT * " +
            "FROM ${Cart.TABLE_NAME} " +
            "WHERE ${Cart.PRODUCT_COUNT} > '0'" +
            "LIMIT $startPosition, $CartItemCount"
    }

    fun getTotalPriceSql(): String {
        return "SELECT * " +
            "FROM ${Cart.TABLE_NAME} " +
            "WHERE ${Cart.PRODUCT_COUNT} > '0' and ${Cart.IS_SELECTED}= 'y'"
    }

    fun getProductCount(productId: Long): String {
        return "SELECT ${Cart.PRODUCT_COUNT} " +
            "FROM ${Cart.TABLE_NAME} " +
            "WHERE ${Cart.PRODUCT_ID} = $productId"
    }

    object Cart : BaseColumns {
        const val TABLE_NAME = "Cart"
        const val PRODUCT_ID = "ProductId"
        const val PRODUCT_COUNT = "ProductCount"
        const val IS_SELECTED = "IsSelected"
    }
}
