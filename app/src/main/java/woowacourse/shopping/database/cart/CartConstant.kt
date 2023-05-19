package woowacourse.shopping.database.cart

import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.model.CartProduct

object CartConstant : BaseColumns {
    private const val TABLE_NAME = "cart_products"
    private const val TABLE_COLUMN_ID = "product_id"
    private const val TABLE_COLUMN_NAME = "product_name"
    private const val TABLE_COLUMN_COUNT = "product_count"
    private const val TABLE_COLUMN_CHECKED = "product_checked"
    private const val TABLE_COLUMN_PRICE = "product_price"
    private const val TABLE_COLUMN_IMAGE_URL = "product_img_url"
    private const val TABLE_COLUMN_SAVE_TIME = "product_save_time"

    fun getCreateTableQuery(): String {
        return """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $TABLE_COLUMN_ID INTEGER PRIMARY KEY,
                $TABLE_COLUMN_NAME TEXT,
                $TABLE_COLUMN_COUNT INTEGER,
                $TABLE_COLUMN_CHECKED INTEGER,
                $TABLE_COLUMN_PRICE INTEGER,
                $TABLE_COLUMN_IMAGE_URL TEXT,
                $TABLE_COLUMN_SAVE_TIME INTEGER)
        """.trimIndent()
    }

    fun getUpdateTableQuery(): String {
        return "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    fun getDeleteQuery(id: Int): String {
        return "DELETE FROM $TABLE_NAME WHERE $TABLE_COLUMN_ID = $id"
    }

    fun getInsertQuery(cartProduct: CartProduct): String {
        return """
            INSERT OR IGNORE INTO $TABLE_NAME (
                $TABLE_COLUMN_ID,
                $TABLE_COLUMN_NAME,
                $TABLE_COLUMN_COUNT,
                $TABLE_COLUMN_CHECKED,
                $TABLE_COLUMN_PRICE,
                $TABLE_COLUMN_IMAGE_URL,
                $TABLE_COLUMN_SAVE_TIME) VALUES (
                ${cartProduct.id},
                '${cartProduct.name}',
                ${cartProduct.count},
                ${cartProduct.checked},
                ${cartProduct.price},
                '${cartProduct.imageUrl}',
                ${System.currentTimeMillis()})
        """.trimIndent()
    }

    fun getUpdateCountQuery(id: Int, count: Int): String {
        return """
            UPDATE $TABLE_NAME SET 
                $TABLE_COLUMN_COUNT = $count 
            WHERE $TABLE_COLUMN_ID = $id
        """.trimIndent()
    }

    fun getUpdateCheckedQuery(id: Int, selected: Boolean): String {
        return """
            UPDATE $TABLE_NAME
            SET $TABLE_COLUMN_CHECKED = ${if (selected) 1 else 0} 
            WHERE $TABLE_COLUMN_ID = $id
        """.trimIndent()
    }

    fun getGetAllQuery(): String {
        return "SELECT * FROM $TABLE_NAME ORDER BY $TABLE_COLUMN_SAVE_TIME"
    }

    fun fromCursor(cursor: Cursor): CartProduct {
        return CartProduct(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME)),
            count = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_COUNT)),
            checked = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_CHECKED)) == 1,
            price = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRICE)),
            imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_IMAGE_URL))
        )
    }
}
