package woowacourse.shopping.data.recentviewed.cache

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.ShoppingDao
import woowacourse.shopping.data.recentviewed.RecentViewedDBContract
import woowacourse.shopping.data.recentviewed.RecentViewedProductEntity

class RecentViewedProductCacheImpl(
    context: Context,
    shoppingDao: ShoppingDao = ShoppingDao(context),
) : RecentViewedProductCache {

    private val shoppingDB: SQLiteDatabase = shoppingDao.writableDatabase

    override fun addToRecentViewedProduct(id: Int) {
        val values = ContentValues().apply {
            put(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID, id)
        }
        shoppingDB.delete(
            RecentViewedDBContract.TABLE_NAME,
            "${RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID} = ?",
            arrayOf(id.toString())
        )
        shoppingDB.insert(RecentViewedDBContract.TABLE_NAME, null, values)
    }

    override fun getRecentViewedProducts(): List<RecentViewedProductEntity> {
        val recentViewedCursor = shoppingDB.query(
            RecentViewedDBContract.TABLE_NAME,
            arrayOf(
                RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID
            ),
            null, null, null, null, null
        )
        val recentViewedProducts = mutableListOf<RecentViewedProductEntity>()

        return with(recentViewedCursor) {
            buildList {
                while (moveToNext()) {
                    val id =
                        getInt(getColumnIndexOrThrow(RecentViewedDBContract.RECENT_VIEWED_PRODUCT_ID))
                    add(RecentViewedProductEntity(id))
                }
                close()
            }.reversed()
        }
    }

    override fun removeRecentViewedProduct() {
        shoppingDB.delete(
            RecentViewedDBContract.TABLE_NAME,
            "ROWID = (SELECT MIN(ROWID) FROM ${RecentViewedDBContract.TABLE_NAME})",
            null
        )
    }

    override fun getLatestViewedProduct(): RecentViewedProductEntity? {
        val recentViewedProducts = getRecentViewedProducts()

        if (recentViewedProducts.isEmpty()) return null

        return RecentViewedProductEntity(
            id = recentViewedProducts.first().id
        )
    }
}
