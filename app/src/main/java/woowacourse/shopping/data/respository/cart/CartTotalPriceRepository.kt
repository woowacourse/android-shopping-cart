package woowacourse.shopping.data.respository.cart

interface CartTotalPriceRepository {
    fun getTotalPrice(): Int
    fun setTotalPrice(price: Int)
}
