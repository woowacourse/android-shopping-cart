package woowacourse.shopping.datas

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_ID
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_IMAGE
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_NAME
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_PRICE
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_TIME
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.TABLE_NAME
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductDBRepository(private val database: SQLiteDatabase) : RecentRepository {
    override fun getAll(): List<RecentProductUIModel> {
        val products = mutableListOf<RecentProductUIModel>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val recentProductUIModel =
                    RecentProductUIModel(
                        time = it.getLong(it.getColumnIndexOrThrow(KEY_TIME)),
                        ProductUIModel(
                            id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                            name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                            imageUrl = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                            price = it.getInt(it.getColumnIndexOrThrow(KEY_PRICE)),
                        )
                    )
                products.add(recentProductUIModel)
            }
        }
        return products.distinctBy { it.product }.sortedBy { it.time }.takeLast(
            MAX_RECENT_PRODUCTS_SIZE
        ).reversed()
    }

    override fun insert(recentProduct: RecentProductUIModel) {
        database.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $KEY_ID = '${recentProduct.product.id}' "
        )

        val record = ContentValues().apply {
            put(KEY_TIME, recentProduct.time)
            put(KEY_ID, recentProduct.product.id)
            put(KEY_NAME, recentProduct.product.name)
            put(KEY_IMAGE, recentProduct.product.imageUrl)
            put(KEY_PRICE, recentProduct.product.price)
        }
        database.insert(TABLE_NAME, null, record)
    }

    override fun remove(recentProduct: RecentProductUIModel) {
        database.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $KEY_ID = '${recentProduct.product.id}' "
        )
    }

    override fun clear() {
        database.execSQL("DELETE FROM $TABLE_NAME")
    }

    override fun isEmpty(): Boolean =
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use { it.count == 0 }

    override fun close() {
        database.close()
    }

    companion object {
        private const val MAX_RECENT_PRODUCTS_SIZE = 10
    }
}
