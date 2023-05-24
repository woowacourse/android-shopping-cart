package woowacourse.shopping.database.cart

import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.model.Product

object ProductConstant : BaseColumns {
    private const val TABLE_NAME = "base_products"
    private const val TABLE_COLUMN_ID = "product_id"
    private const val TABLE_COLUMN_NAME = "product_name"
    private const val TABLE_COLUMN_PRICE = "product_price"
    private const val TABLE_COLUMN_IMAGE_URL = "product_img_url"

    fun getCreateTableQuery(): String {
        return """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $TABLE_COLUMN_ID INTEGER PRIMARY KEY,
                $TABLE_COLUMN_NAME TEXT,
                $TABLE_COLUMN_PRICE INTEGER,
                $TABLE_COLUMN_IMAGE_URL TEXT)
        """.trimIndent()
    }

    fun getUpdateTableQuery(): String {
        return "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    fun getGetQuery(id: Int): String {
        return "SELECT * FROM $TABLE_NAME WHERE $TABLE_COLUMN_ID = $id"
    }

    fun getGetAllQuery(): String {
        return "SELECT * FROM $TABLE_NAME"
    }

    fun getGetNextQuery(limit: Int, offset: Int): String {
        return "SELECT * FROM $TABLE_NAME DESC LIMIT $limit OFFSET $offset"
    }

    fun getInsertQuery(product: Product): String {
        return """
            INSERT OR IGNORE INTO $TABLE_NAME (
                $TABLE_COLUMN_ID,
                $TABLE_COLUMN_NAME,
                $TABLE_COLUMN_PRICE,
                $TABLE_COLUMN_IMAGE_URL) VALUES (
                ${product.id},
                '${product.name}',
                ${product.price},
                '${product.imageUrl}')
        """.trimIndent()
    }

    fun fromCursor(cursor: Cursor): Product {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME))
        val price = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRICE))
        val imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_IMAGE_URL))
        return Product(id, name, price, imageUrl)
    }
}
