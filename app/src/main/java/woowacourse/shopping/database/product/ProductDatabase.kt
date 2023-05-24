package woowacourse.shopping.database.product

import android.content.Context
import woowacourse.shopping.database.ShoppingDBHelper
import woowacourse.shopping.database.cart.ProductConstant
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository

class ProductDatabase(context: Context) : ProductRepository {
    private val db = ShoppingDBHelper(context).writableDatabase

    private var offset = 0
    override fun getAll(): List<Product> {
        val products = mutableListOf<Product>()
        db.rawQuery(ProductConstant.getGetAllQuery(), null).use {
            while (it.moveToNext()) {
                products.add(ProductConstant.fromCursor(it))
            }
        }
        return products
    }

    override fun getNext(count: Int): List<Product> {
        val products = mutableListOf<Product>()
        db.rawQuery(ProductConstant.getGetNextQuery(count, offset), null).use {
            while (it.moveToNext()) {
                products.add(ProductConstant.fromCursor(it))
                offset++
            }
        }
        return products
    }

    override fun findById(id: Int): Product {
        db.rawQuery(ProductConstant.getGetQuery(id), null).use {
            it.moveToNext()
            return ProductConstant.fromCursor(it)
        }
    }

    override fun insert(product: Product): Int {
        db.execSQL(ProductConstant.getInsertQuery(product))
        return product.id
    }
}
