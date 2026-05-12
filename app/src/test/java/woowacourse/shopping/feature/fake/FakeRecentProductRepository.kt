package woowacourse.shopping.feature.fake

import woowacourse.shopping.data.repository.recentproduct.RecentProductRepository

class FakeRecentProductRepository(
    initial: List<String> = emptyList(),
) : RecentProductRepository {

    private val recents: ArrayDeque<String> = ArrayDeque(initial)
    val insertedIds: MutableList<String> = mutableListOf()

    override suspend fun loadProducts(): List<String> = recents.toList()

    override suspend fun insert(id: String) {
        insertedIds.add(id)
        recents.remove(id)
        recents.addFirst(id)
        while (recents.size > MAX_SIZE) {
            recents.removeLast()
        }
    }

    companion object {
        private const val MAX_SIZE = 10
    }
}
