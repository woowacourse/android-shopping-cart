package woowacourse.shopping.data.history.repository

import woowacourse.shopping.domain.model.Cart

interface HistoryRepository {
    fun getAll(callback: (List<Cart>) -> Unit)

    fun insert(cart: Cart)

    fun findLatest(callback: (Cart?) -> Unit)
}
