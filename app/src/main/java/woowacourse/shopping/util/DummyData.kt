package woowacourse.shopping.util

import woowacourse.shopping.R
import woowacourse.shopping.domain.model.Goods

object DummyData {
    private val SWEET_CORN =
        Goods(
            "[든든] 동원 스위트콘",
            99800,
            R.drawable.sweet_corn,
        )

    private val MILK_TEA =
        Goods(
            "PET보틀-밀크티입니다람쥐",
            12000,
            R.drawable.sweet_corn,
        )

    private val SQUARE_TEA =
        Goods(
            "PET보틀_정사각형정삼각형정오각형",
            10000,
            R.drawable.sweet_corn,
        )

    val GOODS =
        listOf(
            SWEET_CORN,
            MILK_TEA,
            SQUARE_TEA,
            SWEET_CORN,
            MILK_TEA,
            SQUARE_TEA,
            SWEET_CORN,
            MILK_TEA,
            SQUARE_TEA,
        )
}
