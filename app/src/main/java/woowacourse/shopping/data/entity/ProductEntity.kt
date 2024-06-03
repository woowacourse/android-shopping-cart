package woowacourse.shopping.data.entity

data class ProductEntity(
    val id: Long,
    val title: String,
    val price: Int,
    val imageUrl: String,
) {
    companion object {
        val STUB_LIST = makeSTUB()

        private fun makeSTUB(): List<ProductEntity> {
            val products = mutableListOf<ProductEntity>()

            for (i in 0 until 60) {
                when (i % 6) {
                    0 ->
                        products.add(
                            ProductEntity(
                                i.toLong(),
                                "PET보틀-납작(260ml)",
                                61800,
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/fed0b624-737a-4e4a-be3e-2e8a8cd953fc",
                            ),
                        )

                    1 ->
                        products.add(
                            ProductEntity(
                                i.toLong(),
                                "PET보틀-원형(200ml)",
                                88800,
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/015018f9-f668-4e71-94c8-cb214f034321",
                            ),
                        )

                    2 ->
                        products.add(
                            ProductEntity(
                                i.toLong(),
                                "PET보틀-정사각(370ml)",
                                41000,
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/0d14a4d3-312d-4688-8699-f2c269c70ce5",
                            ),
                        )

                    3 ->
                        products.add(
                            ProductEntity(
                                i.toLong(),
                                "PET보틀-밀크티(250ml)",
                                12000,
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/11533147-d5b7-4d41-9668-03d8b5dcbb7b",
                            ),
                        )

                    4 ->
                        products.add(
                            ProductEntity(
                                i.toLong(),
                                "PET보틀-단지(100ml)",
                                10000,
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/0277eaa6-f0a4-4be6-8b9d-7b732cd411f4",
                            ),
                        )

                    5 ->
                        products.add(
                            ProductEntity(
                                i.toLong(),
                                "PET보틀-단지(150ml)",
                                12000,
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/0e4959a4-08a7-40e4-a876-a832495233be",
                            ),
                        )
                }
            }

            return products.toList()
        }
    }
}
