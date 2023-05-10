package woowacourse.shopping.util

import android.content.Context
import woowacourse.shopping.data.product.ProductDao

fun initProducts(context: Context) {
    val productDao = ProductDao(context)
    productDao.addProductEntity(
        name = "제네시스 Gv70",
        itemImage = "",
        price = 40_000_000,
    )
    productDao.addProductEntity(
        name = "제네시스 G80",
        itemImage = "",
        price = 40_000_000,
    )
    productDao.addProductEntity(
        name = "포르쉐 파나메라",
        itemImage = "",
        price = 150_000_000,
    )
}
