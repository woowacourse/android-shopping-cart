package woowacourse.shopping.datas

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.shopping.domain.Price
import com.shopping.domain.Product
import com.shopping.domain.RecentProduct
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_ID
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_IMAGE
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_NAME
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_PRICE
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.KEY_TIME
import woowacourse.shopping.datas.RecentProductDBHelper.Companion.TABLE_NAME

class RecentProductDBRepository(private val database: SQLiteDatabase) : RecentRepository {
    override fun getAll(): List<RecentProduct> {
        val products = mutableListOf<RecentProduct>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val recentProduct =
                    RecentProduct(
                        time = it.getLong(it.getColumnIndexOrThrow(KEY_TIME)),
                        Product(
                            id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                            name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                            imageUrl = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                            price = Price(it.getInt(it.getColumnIndexOrThrow(KEY_PRICE))),
                        )
                    )
                products.add(recentProduct)
            }
        }
        return products.distinctBy { it.product }.sortedBy { it.time }.takeLast(
            MAX_RECENT_PRODUCTS_SIZE
        ).reversed()
    }

    override fun insert(recentProduct: RecentProduct) {
        database.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $KEY_ID = '${recentProduct.product.id}' "
        )

        val record = ContentValues().apply {
            put(KEY_TIME, recentProduct.time)
            put(KEY_ID, recentProduct.product.id)
            put(KEY_NAME, recentProduct.product.name)
            put(KEY_IMAGE, recentProduct.product.imageUrl)
            put(KEY_PRICE, recentProduct.product.price.value)
        }
        database.insert(TABLE_NAME, null, record)
    }

    override fun remove(recentProduct: RecentProduct) {
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
