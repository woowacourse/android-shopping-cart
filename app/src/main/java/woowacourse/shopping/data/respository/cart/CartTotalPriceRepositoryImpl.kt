package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.data.local.sharedpreference.SharedPreference

class CartTotalPriceRepositoryImpl(private val sharedPreferences: SharedPreference) :
    CartTotalPriceRepository {
    override fun getTotalPrice(): Int {
        return sharedPreferences.getInt(KEY)
    }

    override fun setTotalPrice(price: Int) {
        return sharedPreferences.putInt(KEY, price)
    }

    companion object {
        private const val KEY = "total_price"
    }
}
