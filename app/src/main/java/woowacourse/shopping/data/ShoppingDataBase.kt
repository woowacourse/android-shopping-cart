package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods

object ShoppingDataBase : ShoppingRepository {
    private const val INDEX_OFFSET: Int = 1
    private const val MINIMUM_INDEX: Int = 0
    private val selectedGoods: MutableList<Goods> = mutableListOf()

    override fun addItem(goods: Goods) {
        selectedGoods.add(goods)
    }

    override fun removeItem(goods: Goods) {
        selectedGoods.remove(goods)
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<Goods> {
        val fromIndex = (page - INDEX_OFFSET) * count
        val toIndex = minOf(fromIndex + count, selectedGoods.size)

        return if (fromIndex in MINIMUM_INDEX until toIndex) {
            selectedGoods.subList(fromIndex, toIndex).toList()
        } else {
            emptyList()
        }
    }
}
