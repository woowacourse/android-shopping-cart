package woowacourse.shopping.data.shopping.product

import woowacourse.shopping.remote.service.DefaultShoppingProductService

object ProductDataSourceInjector {
    @Volatile
    private var instance: ProductDataSource? = null

    fun productDataSource(): ProductDataSource =
        instance ?: synchronized(this) {
            instance ?: DefaultProductDataSource(DefaultShoppingProductService.instance()).also {
                instance = it
            }
        }
}
