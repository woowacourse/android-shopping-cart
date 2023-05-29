package woowacourse.shopping.data.dataSource.local.recentlyViewed

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.db.WoowaShoppingContract.RecentlyViewed.TABLE_COLUMN_PRODUCT_ID
import woowacourse.shopping.data.db.WoowaShoppingContract.RecentlyViewed.TABLE_NAME
import woowacourse.shopping.data.db.WoowaShoppingDbHelper
import woowacourse.shopping.data.entity.RecentlyViewedEntity

class RecentlyViewedDao(context: Context) : RecentlyViewedDataSource {
    private val shoppingDb by lazy { WoowaShoppingDbHelper(context).readableDatabase }

    override fun getRecentlyViewedProducts(unit: Int): List<RecentlyViewedEntity> {
        val query = "SELECT * FROM $TABLE_NAME LIMIT $unit"
        val cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<RecentlyViewedEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readRecentlyViewed(cursor))
        }
        cursor.close()
        return itemContainer.reversed()
    }

    override fun getLastViewedProduct(): List<RecentlyViewedEntity> {
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<RecentlyViewedEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readRecentlyViewed(cursor))
        }
        cursor.close()
        return itemContainer.reversed()
    }

    override fun addRecentlyViewedProduct(productId: Long, unit: Int): Long {
        val data = ContentValues()
        data.put(TABLE_COLUMN_PRODUCT_ID, productId)
        val id = shoppingDb.insert(TABLE_NAME, null, data)

        val query = """
            DELETE FROM $TABLE_NAME WHERE ${BaseColumns._ID} NOT IN (
            SELECT ${BaseColumns._ID} FROM $TABLE_NAME ORDER BY ${BaseColumns._ID} DESC LIMIT $unit
            )
        """.trimIndent()
        shoppingDb.execSQL(query)
        return id
    }

    override fun deleteRecentlyViewedProduct(productId: Long) {
        shoppingDb.delete(
            TABLE_NAME,
            "$TABLE_COLUMN_PRODUCT_ID=?",
            arrayOf(productId.toString()),
        )
    }

    private fun readRecentlyViewed(cursor: Cursor): RecentlyViewedEntity {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val productId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRODUCT_ID))
        return RecentlyViewedEntity(id, productId)
    }
}
