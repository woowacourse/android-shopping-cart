package woowacourse.shopping.database.product

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.database.cart.ProductConstant
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository

class ProductDatabase(private val shoppingDb: SQLiteDatabase) : ProductRepository {
    override fun getAll(): List<Product> {
        val products = mutableListOf<Product>()
        shoppingDb.rawQuery(ProductConstant.getGetAllQuery(), null).use {
            while (it.moveToNext()) {
                products.add(ProductConstant.fromCursor(it))
            }
        }
        return products
    }

    override fun getNext(count: Int): List<Product> {
        val products = mutableListOf<Product>()
        shoppingDb.rawQuery(ProductConstant.getGetNextQuery(count), null).use {
            while (it.moveToNext()) {
                products.add(ProductConstant.fromCursor(it))
            }
        }
        return products
    }

    override fun findById(id: Int): Product {
        shoppingDb.rawQuery(ProductConstant.getGetQuery(id), null).use {
            it.moveToNext()
            return ProductConstant.fromCursor(it)
        }
    }

    override fun insert(product: Product) {
        shoppingDb.execSQL(ProductConstant.getInsertQuery(product))
    }
}
