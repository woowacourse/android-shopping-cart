package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

val dummyProductsFixture =
    listOf(
        Product(
            id = 1,
            name = "이상해씨",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000101.png",
            Price(10_000),
        ),
        Product(
            id = 2,
            name = "이상해풀",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000201.png",
            Price(10_000),
        ),
        Product(
            id = 3,
            name = "이상해꽃",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000301.png",
            Price(10_000),
        ),
        Product(
            id = 4,
            name = "파이리",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000401.png",
            Price(10_000),
        ),
        Product(
            id = 5,
            name = "리자드",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000501.png",
            Price(10_000),
        ),
        Product(
            id = 6,
            name = "리자몽",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000601.png",
            Price(10_000),
        ),
        Product(
            id = 7,
            name = "꼬부기",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000701.png",
            Price(10_000),
        ),
        Product(
            id = 8,
            name = "어니부기",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000801.png",
            Price(10_000),
        ),
        Product(
            id = 9,
            name = "거북왕",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/000901.png",
            Price(10_000),
        ),
        Product(
            id = 10,
            name = "캐터피",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001001.png",
            Price(10_000),
        ),
        Product(
            id = 11,
            name = "단데기",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001101.png",
            Price(10_000),
        ),
        Product(
            id = 12,
            name = "버터플",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001201.png",
            Price(10_000),
        ),
        Product(
            id = 13,
            name = "뿔충이",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001301.png",
            Price(10_000),
        ),
        Product(
            id = 14,
            name = "딱충이",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001401.png",
            Price(10_000),
        ),
        Product(
            id = 15,
            name = "독침붕",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001501.png",
            Price(10_000),
        ),
        Product(
            id = 16,
            name = "구구",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001601.png",
            Price(10_000),
        ),
        Product(
            id = 17,
            name = "피죤",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001701.png",
            Price(10_000),
        ),
        Product(
            id = 18,
            name = "피죤투",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001801.png",
            Price(10_000),
        ),
        Product(
            id = 19,
            name = "꼬렛",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/001901.png",
            Price(10_000),
        ),
        Product(
            id = 20,
            name = "레트라",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002001.png",
            Price(10_000),
        ),
        Product(
            id = 21,
            name = "깨비참",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002101.png",
            Price(10_000),
        ),
        Product(
            id = 22,
            name = "깨비드릴조",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002201.png",
            Price(10_000),
        ),
        Product(
            id = 23,
            name = "아보",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002301.png",
            Price(10_000),
        ),
        Product(
            id = 24,
            name = "아보크",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002401.png",
            Price(10_000),
        ),
        Product(
            id = 25,
            name = "피카츄",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002501.png",
            Price(10_000),
        ),
        Product(
            id = 26,
            name = "라이츄",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002601.png",
            Price(10_000),
        ),
        Product(
            id = 27,
            name = "모래두지",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002701.png",
            Price(10_000),
        ),
        Product(
            id = 28,
            name = "고지",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002801.png",
            Price(10_000),
        ),
        Product(
            id = 29,
            name = "니드런♀",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/002901.png",
            Price(10_000),
        ),
        Product(
            id = 30,
            name = "니드리나",
            imageUrl = "https://data1.pokemonkorea.co.kr/newdata/pokedex/mid/003001.png",
            Price(10_000),
        ),
    ).sortedBy { it.id }
