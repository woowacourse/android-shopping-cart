package woowacourse.shopping

import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.InMemoryCartRepository

object AppContainer {
    val cartRepository: CartRepository = InMemoryCartRepository()
}