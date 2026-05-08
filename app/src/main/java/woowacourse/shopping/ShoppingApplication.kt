package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository

class ShoppingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        InMemoryCartRepository.initialize(this)
    }
}