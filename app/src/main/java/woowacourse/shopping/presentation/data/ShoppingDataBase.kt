package woowacourse.shopping.presentation.data

import woowacourse.shopping.domain.model.Goods

object ShoppingDataBase {
    private val selectedGoods: MutableList<Goods> = mutableListOf()

    fun addItem(goods: Goods) {
        selectedGoods.add(goods)
    }

    fun removeItem(goods: Goods) {
        selectedGoods.remove(goods)
    }

    fun getAll(): List<Goods> = selectedGoods.toList()
}
