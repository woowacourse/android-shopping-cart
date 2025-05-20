package woowacourse.shopping.domain.model

data class Goods(
    val name: String,
    val price: Int,
    val thumbnailUrl: String,
    val id: Long = 0,
) {
    companion object {
        private val _dummyGoods =
            listOf(
                Goods(
                    "[굿즈-키홀더] 봇치 더 록! 애니판 테쿠토코 쵸이데카 아크릴키홀더B",
                    13500,
                    "https://animate.godohosting.com/Goods/4522776264043.jpg",
                ),
                Goods(
                    "[굿즈-스탠드팝] 진격의 거인 아크릴스탠드 리바이A",
                    18000,
                    "https://animate.godohosting.com/Goods/4582682486458.jpg",
                ),
                Goods(
                    "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/렘",
                    10500,
                    "https://animate.godohosting.com/Goods/4550621224034.jpg",
                ),
                Goods(
                    "[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/람",
                    10500,
                    "https://animate.godohosting.com/Goods/4550621224041.jpg",
                ),
                Goods(
                    "[굿즈-클리어파일] SPY x FAMILY WIT스토어선행 클리어파일 샵 비주얼",
                    6000,
                    "https://animate.godohosting.com/Goods/4549743820774.jpg",
                ),
                Goods(
                    "[굿즈-마우스패드] BanG Dream! 뱅드림 Ani-Art 마우스패드 제5탄 Pastel＊Palettes",
                    27000,
                    "https://animate.godohosting.com/Goods/4571622733271.jpg",
                ),
                Goods(
                    "[굿즈-스탠드팝] VOCALOID 하츠네 미쿠 39Culture 2024 지방 아크릴 디오라마 후쿠오카",
                    18000,
                    "https://animate.godohosting.com/Goods/4582660578458.jpg",
                ),
            )

        val dummyGoods = List(6) { _dummyGoods }.flatten()
    }
}
