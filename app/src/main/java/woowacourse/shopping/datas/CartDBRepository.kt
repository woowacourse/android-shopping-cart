package woowacourse.shopping.datas

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.shopping.domain.CartProduct
import com.shopping.domain.Price
import com.shopping.domain.Product
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_COUNT
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_ID
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_IMAGE
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_IS_CHECKED
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_NAME
import woowacourse.shopping.datas.CartDBHelper.Companion.KEY_PRICE
import woowacourse.shopping.datas.CartDBHelper.Companion.TABLE_NAME

class CartDBRepository(private val database: SQLiteDatabase) : CartRepository {
    override fun getAll(): List<CartProduct> {
        val products = mutableListOf<CartProduct>()
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val cartProduct =
                    CartProduct(
                        isPicked = it.getInt(it.getColumnIndexOrThrow(KEY_IS_CHECKED)) == TRUE,
                        count = it.getInt(it.getColumnIndexOrThrow(KEY_COUNT)),
                        Product(
                            id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                            name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                            imageUrl = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                            price = Price(it.getInt(it.getColumnIndexOrThrow(KEY_PRICE))),
                        )
                    )
                products.add(cartProduct)
            }
        }
        return products
    }

    override fun getUnitData(unitSize: Int, pageNumber: Int): List<CartProduct> {
        val products = mutableListOf<CartProduct>()
        database.rawQuery(
            "SELECT * FROM $TABLE_NAME LIMIT $unitSize OFFSET '${5 * (pageNumber - 1)}'",
            null
        ).use {
            while (it.moveToNext()) {
                val cartProduct =
                    CartProduct(
                        isPicked = it.getInt(it.getColumnIndexOrThrow(KEY_IS_CHECKED)) == TRUE,
                        count = it.getInt(it.getColumnIndexOrThrow(KEY_COUNT)),
                        Product(
                            id = it.getInt(it.getColumnIndexOrThrow(KEY_ID)),
                            name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                            imageUrl = it.getString(it.getColumnIndexOrThrow(KEY_IMAGE)),
                            price = Price(it.getInt(it.getColumnIndexOrThrow(KEY_PRICE))),
                        )
                    )
                products.add(cartProduct)
            }
        }
        return products
    }

    override fun insert(cartProduct: CartProduct) {
        val record = ContentValues().apply {
            put(KEY_IS_CHECKED, if (cartProduct.isPicked) TRUE else FALSE)
            put(KEY_COUNT, 1)
            put(KEY_ID, cartProduct.product.id)
            put(KEY_NAME, cartProduct.product.name)
            put(KEY_IMAGE, cartProduct.product.imageUrl)
            put(KEY_PRICE, cartProduct.product.price.value)
        }
        database.insert(TABLE_NAME, null, record)
    }

    override fun updateProductIsPicked(productId: Int, isPicked: Boolean) {
        val selectionStatus = if (isPicked) TRUE else FALSE
        val contentValues = ContentValues().apply {
            put(KEY_IS_CHECKED, selectionStatus)
        }
        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(productId.toString())
        database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    override fun updateProductCount(productId: Int, count: Int) {
        val contentValues = ContentValues().apply {
            put(KEY_COUNT, count)
        }
        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(productId.toString())
        database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    override fun remove(productId: Int) {
        database.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $KEY_ID = '$productId' "
        )
    }

    override fun getSize(): Int {
        database.rawQuery("SELECT * FROM $TABLE_NAME", null).use {
            return it.count
        }
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
        private const val TRUE = 1
        private const val FALSE = 0
    }
}
