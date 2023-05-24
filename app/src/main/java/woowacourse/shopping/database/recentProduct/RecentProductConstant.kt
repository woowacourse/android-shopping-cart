package woowacourse.shopping.database.recentProduct

import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.model.RecentProduct

object RecentProductConstant : BaseColumns {
    private const val TABLE_NAME = "recent_product"
    private const val TABLE_COLUMN_ID = "id"
    private const val TABLE_COLUMN_NAME = "name"
    private const val TABLE_COLUMN_PRICE = "price"
    private const val TABLE_COLUMN_IMAGE_URL = "image_url"
    private const val TABLE_COLUMN_SAVE_TIME = "time"

    fun getCreateTableQuery(): String {
        return """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $TABLE_COLUMN_ID INTEGER PRIMARY KEY,
                $TABLE_COLUMN_NAME TEXT,
                $TABLE_COLUMN_PRICE INTEGER,
                $TABLE_COLUMN_IMAGE_URL TEXT,
                $TABLE_COLUMN_SAVE_TIME INTEGER)
        """.trimIndent()
    }

    fun getUpdateTableQuery(): String {
        return "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    fun getInsertQuery(product: RecentProduct): String {
        return """
            INSERT OR IGNORE INTO $TABLE_NAME (
                $TABLE_COLUMN_ID,
                $TABLE_COLUMN_NAME,
                $TABLE_COLUMN_PRICE,
                $TABLE_COLUMN_IMAGE_URL,
                $TABLE_COLUMN_SAVE_TIME) VALUES (
                ${product.id},
                '${product.name}',
                ${product.price},
                '${product.imageUrl}',
                ${System.currentTimeMillis()})
        """.trimIndent()
    }

    fun getGetRecentProductQuery(limit: Int): String {
        return "SELECT * FROM $TABLE_NAME ORDER BY $TABLE_COLUMN_SAVE_TIME DESC LIMIT $limit"
    }

    fun getDeleteQuery(id: Int): String {
        return "DELETE FROM $TABLE_NAME WHERE $TABLE_COLUMN_ID = $id"
    }

    fun getGetQuery(id: Int): String {
        return "SELECT * FROM $TABLE_NAME WHERE $TABLE_COLUMN_ID = $id"
    }

    fun fromCursor(cursor: Cursor): RecentProduct {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME))
        val price = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRICE))
        val imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_IMAGE_URL))
        return RecentProduct(id, name, price, imageUrl)
    }
}
