package woowacourse.shopping.domain.model

import androidx.annotation.DrawableRes
import woowacourse.shopping.R

data class Goods(
    val name: String,
    val price: Int,
    @DrawableRes val thumbnail: Int,
) {
    companion object {
        val dummyGoods = listOf(
            Goods("아주아주긴이름입니다이건긴이름", 10000, R.drawable.ic_launcher_background),
            Goods("메다천재", 0, R.drawable.ic_launcher_background),
            Goods("메다짱", 0, R.drawable.ic_launcher_background),
            Goods("메다귀요미", 0, R.drawable.ic_launcher_background)
        )
    }
}