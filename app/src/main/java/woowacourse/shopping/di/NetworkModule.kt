package woowacourse.shopping.di

import woowacourse.shopping.data.remote.ProductMockWebServer
import woowacourse.shopping.data.remote.ProductService

object NetworkModule {
    fun provideProductService(): ProductService = ProductMockWebServer()
}
