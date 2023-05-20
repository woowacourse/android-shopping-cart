package woowacourse.shopping.data.database.dao.basket

import android.annotation.SuppressLint
import android.content.ContentValues
import android.provider.BaseColumns
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.contract.BasketContract
import woowacourse.shopping.data.database.contract.ProductContract
import woowacourse.shopping.data.model.BasketProduct
import woowacourse.shopping.data.model.DataBasket
import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.data.model.ProductCount
import woowacourse.shopping.util.extension.safeSubList

class BasketDaoImpl(private val database: ShoppingDatabase) : BasketDao {
    @SuppressLint("Range")
    override fun getProductByPage(page: DataPageNumber): DataBasket {
        val basketProducts = mutableListOf<BasketProduct>()

        val db = database.writableDatabase
        val cursor = db.rawQuery(GET_ALL_BASKET_PRODUCT_QUERY, null)

        while (cursor.moveToNext()) {
            val basketId: Int =
                cursor.getInt(cursor.getColumnIndex(BasketContract.BASKET_ID))
            val productId: Int =
                cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name: String =
                cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME))
            val price: DataPrice =
                DataPrice(cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRICE)))
            val imageUrl: String =
                cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL))
            val count: Int =
                cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_COUNT))
            val isChecked: Int =
                cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_CHECKED))
            val product = Product(productId, name, price, imageUrl)
            basketProducts.add(BasketProduct(basketId, product, ProductCount(count), isChecked))
        }
        cursor.close()
        return DataBasket(basketProducts = basketProducts.safeSubList(page.start, page.end))
    }

    @SuppressLint("Range")
    override fun getProductInBasketByPage(page: DataPageNumber): DataBasket {
        val basketProducts = mutableListOf<BasketProduct>()

        val db = database.writableDatabase
        val cursor = db.rawQuery(GET_ALL_BASKET_PRODUCT_IN_BASKET_QUERY, null)

        while (cursor.moveToNext()) {
            val basketId: Int =
                cursor.getInt(cursor.getColumnIndex(BasketContract.BASKET_ID))
            val productId: Int =
                cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name: String =
                cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME))
            val price: DataPrice =
                DataPrice(cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRICE)))
            val imageUrl: String =
                cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL))
            val count: Int =
                cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_COUNT))
            val isChecked: Int =
                cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_CHECKED))
            val product = Product(productId, name, price, imageUrl)
            basketProducts.add(BasketProduct(basketId, product, ProductCount(count), isChecked))
        }
        cursor.close()

        return DataBasket(basketProducts = basketProducts.safeSubList(page.start, page.end))
    }

    override fun insert(product: Product, count: Int) {
        val contentValues = ContentValues().apply {
            put(BasketContract.PRODUCT_ID, product.id)
            put(BasketContract.COLUMN_CREATED, System.currentTimeMillis())
            put(BasketContract.COLUMN_COUNT, count)
        }

        database.writableDatabase.insert(BasketContract.TABLE_NAME, null, contentValues)
    }

    override fun getProductInBasketSize(): Int {
        val db = database.writableDatabase
        val cursor = db.rawQuery(GET_PRODUCT_IN_BASKET_SIZE, null)
        cursor.moveToNext()

        val productInBasketSize = cursor.getInt(0)
        cursor.close()
        return productInBasketSize
    }

    override fun getTotalPrice(): Int {
        val db = database.writableDatabase
        val cursor = db.rawQuery(GET_TOTAL_PRICE, null)
        cursor.moveToNext()

        val totalPrice = cursor.getInt(0)
        cursor.close()
        return totalPrice
    }

    override fun deleteByProductId(id: Int) {
        database.writableDatabase.delete(
            BasketContract.TABLE_NAME,
            "${BasketContract.PRODUCT_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun update(basketProduct: DataBasketProduct) {
        val contentValues = ContentValues().apply {
            put(BasketContract.PRODUCT_ID, basketProduct.product.id)
            put(BasketContract.COLUMN_COUNT, basketProduct.selectedCount.value)
            put(BasketContract.COLUMN_CHECKED, basketProduct.isChecked)
        }

        database.writableDatabase.update(
            BasketContract.TABLE_NAME,
            contentValues,
            "${BasketContract.PRODUCT_ID} = ?",
            arrayOf(basketProduct.product.id.toString())
        )
    }

    @SuppressLint("Range")
    override fun addProductCount(product: Product, count: Int) {
        when (val originCount = count(product)) {
            0 -> insert(product, count)
            else -> updateCount(product, originCount + count)
        }
    }

    @SuppressLint("Range")
    override fun minusProductCount(product: Product, count: Int) {
        when (val originCount = count(product)) {
            0 -> return
            else -> updateCount(product, originCount - count)
        }
    }

    override fun updateCount(product: Product, count: Int) {
        val contentValues = ContentValues().apply {
            put(BasketContract.PRODUCT_ID, product.id)
            put(BasketContract.COLUMN_COUNT, count)
        }

        database.writableDatabase.update(
            BasketContract.TABLE_NAME,
            contentValues,
            "${BasketContract.PRODUCT_ID} = ?",
            arrayOf(product.id.toString())
        )
    }

    override fun getCheckedProductCount(): Int {
        val db = database.writableDatabase
        val cursor = db.rawQuery(GET_CHECKED_PRODUCT_COUNT, null)
        cursor.moveToNext()

        val checkedProductCount = cursor.getInt(0)
        cursor.close()
        return checkedProductCount
    }

    override fun contains(product: Product): Boolean {
        val db = database.writableDatabase
        val cursor = db.rawQuery(
            """
            SELECT * FROM ${BasketContract.TABLE_NAME}
            WHERE ${BasketContract.PRODUCT_ID} = ?
        """.trimIndent(), arrayOf(product.id.toString())
        )

        val result = cursor.count > 0
        cursor.close()
        return result
    }

    @SuppressLint("Range")
    override fun count(product: Product): Int {
        val db = database.writableDatabase
        val cursor = db.rawQuery(
            """
            SELECT * FROM ${BasketContract.TABLE_NAME} 
            WHERE ${BasketContract.PRODUCT_ID} = ?
        """.trimIndent(), arrayOf(product.id.toString())
        )

        val count = if (cursor.count > 0) {
            cursor.moveToNext()
            val realCount = cursor.getInt(cursor.getColumnIndex(BasketContract.COLUMN_COUNT))
            if (realCount == -1) 0 else realCount
        } else {
            0
        }

        cursor.close()
        return count
    }

    companion object {
        private val GET_ALL_BASKET_PRODUCT_QUERY = """
            SELECT * FROM ${ProductContract.TABLE_NAME} as product 
            LEFT JOIN ${BasketContract.TABLE_NAME} as basket
            ON basket.${BasketContract.PRODUCT_ID} = product.${BaseColumns._ID}
        """.trimIndent()

        private val GET_ALL_BASKET_PRODUCT_IN_BASKET_QUERY = """
            SELECT * FROM ${ProductContract.TABLE_NAME} as product
            LEFT JOIN ${BasketContract.TABLE_NAME} as basket
            ON basket.${BasketContract.PRODUCT_ID} = product.${BaseColumns._ID}
            WHERE ${BasketContract.COLUMN_COUNT} > 0
        """.trimIndent()

        private val GET_PRODUCT_IN_BASKET_SIZE = """
            SELECT SUM(${BasketContract.COLUMN_COUNT}) FROM ${ProductContract.TABLE_NAME} as product
            LEFT JOIN ${BasketContract.TABLE_NAME} as basket
            ON basket.${BasketContract.PRODUCT_ID} = product.${BaseColumns._ID}
            WHERE ${BasketContract.COLUMN_COUNT} > 0
        """.trimIndent()

        private val GET_TOTAL_PRICE = """
            SELECT SUM(${ProductContract.COLUMN_PRICE} * ${BasketContract.COLUMN_COUNT}) FROM ${ProductContract.TABLE_NAME} as product
            LEFT JOIN ${BasketContract.TABLE_NAME} as basket
            ON basket.${BasketContract.PRODUCT_ID} = product.${BaseColumns._ID}
            WHERE ${BasketContract.COLUMN_COUNT} > 0 AND ${BasketContract.COLUMN_CHECKED} = 1
        """.trimIndent()

        private val GET_CHECKED_PRODUCT_COUNT = """
            SELECT COUNT(*) FROM ${ProductContract.TABLE_NAME} as product
            LEFT JOIN ${BasketContract.TABLE_NAME} as basket
            ON basket.${BasketContract.PRODUCT_ID} = product.${BaseColumns._ID}
            WHERE ${BasketContract.COLUMN_COUNT} > 0 AND ${BasketContract.COLUMN_CHECKED} = 1
        """.trimIndent()
    }
}
