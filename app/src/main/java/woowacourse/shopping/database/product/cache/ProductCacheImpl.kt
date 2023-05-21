package woowacourse.shopping.database.product.cache

import android.content.Context
import android.database.Cursor
import woowacourse.shopping.database.ShoppingDao
import woowacourse.shopping.database.product.ProductDBContract
import woowacourse.shopping.database.product.ProductEntity

class ProductCacheImpl(
    context: Context,
    shoppingDao: ShoppingDao = ShoppingDao(context),
) : ProductCache {

    private val shoppingDB = shoppingDao.writableDatabase

    override fun getProductById(id: Int): ProductEntity {
        val cursor = shoppingDB.rawQuery(
            "select * from ${ProductDBContract.TABLE_NAME} where ${ProductDBContract.PRODUCT_ID} = ?",
            arrayOf(id.toString())
        ).apply {
            moveToNext()
        }

        val product = cursor.getProductEntity()
        cursor.close()

        return product
    }

    override fun getProductInRange(from: Int, count: Int): List<ProductEntity> {
        val products = mutableListOf<ProductEntity>()
        val query = "SELECT * FROM ${ProductDBContract.TABLE_NAME} LIMIT %s OFFSET %s".format(
            count, from
        )
        val cursor = shoppingDB.rawQuery(query, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    products.add(cursor.getProductEntity())
                } while (moveToNext())
            }
        }
        cursor?.close()

        return products
    }

    private fun Cursor.getProductEntity(): ProductEntity {
        val id = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_ID))
        val img = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_IMG))
        val name = getString(getColumnIndexOrThrow(ProductDBContract.PRODUCT_NAME))
        val price = getInt(getColumnIndexOrThrow(ProductDBContract.PRODUCT_PRICE))
        return ProductEntity(
            id = id,
            name = name,
            imageUrl = img,
            price = price
        )
    }
}
