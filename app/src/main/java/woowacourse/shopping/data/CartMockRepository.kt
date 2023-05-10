package woowacourse.shopping.data

import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ID

object CartMockRepository : CartRepository {
    private val cartProductIDs = mutableListOf<ID>(0, 1, 2, 3, 4)

    override fun findAll(): List<ID> {
        return cartProductIDs
    }

    override fun add(id: Int) {
        cartProductIDs.add(id)
    }

    override fun remove(id: Int) {
        cartProductIDs.remove(id)
    }
}
