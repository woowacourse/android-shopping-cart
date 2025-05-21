package woowacourse.shopping.data

import woowacourse.shopping.domain.model.CartGoods
import woowacourse.shopping.domain.model.Goods

object ShoppingDataBase : ShoppingRepository {
    private const val INDEX_OFFSET: Int = 1
    private const val MINIMUM_INDEX: Int = 0
    private val selectedGoods: MutableList<CartGoods> = mutableListOf()

    override fun getAllGoods(): Set<CartGoods> = selectedGoods.toSet()

    override fun addItemsWithCount(
        goods: Goods,
        count: Int,
    ) {
        updateItem(goods.id, count)
    }

    override fun increaseItemCount(goods: Goods) {
        updateItem(goods.id, 1)
    }

    override fun decreaseItemCount(id: Int) {
        updateItem(id, -1)
    }

    override fun removeItem(id: Int) {
        selectedGoods.removeAll { it.goodsId == id }
    }

    private fun updateItem(
        goodsId: Int,
        countDiff: Int,
    ) {
        val index = selectedGoods.indexOfFirst { it.goodsId == goodsId }

        if (index == -1 && countDiff > 0) {
            selectedGoods.add(CartGoods(goodsId, countDiff))
        } else if (index != -1) {
            val currentItem = selectedGoods[index]
            val updatedCount = currentItem.goodsCount + countDiff

            if (updatedCount <= 0) {
                selectedGoods.removeAt(index)
            } else {
                selectedGoods[index] = currentItem.copy(goodsCount = updatedCount)
            }
        }
    }

    override fun getPagedGoods(
        page: Int,
        count: Int,
    ): List<CartGoods> {
        val fromIndex = (page - INDEX_OFFSET) * count
        val toIndex = minOf(fromIndex + count, selectedGoods.size)

        return if (fromIndex in MINIMUM_INDEX until toIndex) {
            selectedGoods.subList(fromIndex, toIndex).toList()
        } else {
            emptyList()
        }
    }
}
