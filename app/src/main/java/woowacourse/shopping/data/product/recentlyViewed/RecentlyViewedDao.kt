package woowacourse.shopping.data.product.recentlyViewed

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.WoowaShoppingContract.RecentlyViewed.TABLE_COLUMN_PRODUCT_ID
import woowacourse.shopping.data.WoowaShoppingContract.RecentlyViewed.TABLE_NAME
import woowacourse.shopping.data.WoowaShoppingDbHelper

class RecentlyViewedDao : RecentlyViewedDataSource {
    private val shoppingDb by lazy { WoowaShoppingDbHelper().readableDatabase }

    override fun getRecentlyViewedProducts(unit: Int): List<RecentlyViewedEntity> {
        val query = "SELECT * FROM $TABLE_NAME LIMIT $unit"
        val cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<RecentlyViewedEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readRecentlyViewed(cursor))
        }
        cursor.close()
        return itemContainer
    }

    override fun addRecentlyViewedProduct(productId: Long, unit: Int): Long {
        val data = ContentValues()
        data.put(TABLE_COLUMN_PRODUCT_ID, productId)
        val id = shoppingDb.insert(TABLE_NAME, null, data)

        val query = """
            DELETE FROM $TABLE_NAME WHERE ${BaseColumns._ID} NOT IN (
            SELECT ${BaseColumns._ID} FROM $TABLE_NAME ORDER BY ${BaseColumns._ID} DESC LIMIT $unit
            )
            """
        shoppingDb.execSQL(query)
        return id
    }

    private fun readRecentlyViewed(cursor: Cursor): RecentlyViewedEntity {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val productId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRODUCT_ID))
        return RecentlyViewedEntity(id, productId)
    }
}
