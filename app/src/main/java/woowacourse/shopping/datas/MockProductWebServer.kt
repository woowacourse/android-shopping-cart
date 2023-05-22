package woowacourse.shopping.datas

//
// class MockProductWebServer {
//    private val mockWebServer = MockWebServer()
//    val url = mockWebServer.url("").toString()
//
//    init {
//        mockWebServer.url("/")
//        mockWebServer.dispatcher = getDisPatcher()
//    }
//
//    val products = """
//        [
//          {
//            "id": 0,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000113.jpg?1608527683",
//            "name": "PET 보틀 정사각 420",
//            "price": {
//              "amount": 14000
//            }
//          },
//          {
//            "id": 1,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000103.jpg?1608527588",
//            "name": "PET 보틀 정사각 370",
//            "price": {
//              "amount": 13000
//            }
//          },
//          {
//            "id": 2,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000093.jpg?1608527758",
//            "name": "PET 보틀 밀크티 370",
//            "price": {
//              "amount": 13000
//            }
//          },
//          {
//            "id": 3,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000083.jpg?1608190606",
//            "name": "PET 보틀 단지 480",
//            "price": {
//              "amount": 14000
//            }
//          },
//          {
//            "id": 4,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000073.jpg?1608190510",
//            "name": "PET 보틀 삼각 530",
//            "price": {
//              "amount": 15000
//            }
//          },
//          {
//            "id": 5,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000063.jpg?1608190422",
//            "name": "PET 보틀 삼각 330",
//            "price": {
//              "amount": 13000
//            }
//          },
//          {
//            "id": 6,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000053.jpg?1608190250",
//            "name": "PET 보틀 원형 600",
//            "price": {
//              "amount": 16000
//            }
//          },
//          {
//            "id": 7,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000043.jpg?1608190202",
//            "name": "PET 보틀 원형 500",
//            "price": {
//              "amount": 15000
//            }
//          },
//          {
//            "id": 8,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000033.jpg?1608190144",
//            "name": "PET 보틀 원형 300",
//            "price": {
//              "amount": 13000
//            }
//          },
//          {
//            "id": 9,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000023.jpg?1608190024",
//            "name": "PET 보틀 납작 450",
//            "price": {
//              "amount": 14000
//            }
//          },
//          {
//            "id": 10,
//            "image": "http://modooing.co.kr/shopimages/modooin/0010040000013.jpg?1608188560",
//            "name": "PET 보틀 납작 260",
//            "price": {
//              "amount": 12000
//            }
//          },
//          {
//            "id": 11,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000203.jpg?1669862090",
//            "name": "PET 원형 공캔 420",
//            "price": {
//              "amount": 14000
//            }
//          },
//          {
//            "id": 12,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000223.jpg?1673316943",
//            "name": "PET 식품용 페트캔 600",
//            "price": {
//              "amount": 16000
//            }
//          },
//          {
//            "id": 13,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000213.jpg?1673316805",
//            "name": "PET 식품용 페트캔 200",
//            "price": {
//              "amount": 12000
//            }
//          },
//          {
//            "id": 14,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000163.jpg?1670293458",
//            "name": "AL 공캔 실버 355",
//            "price": {
//              "amount": 13000
//            }
//          },
//          {
//            "id": 15,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000153.jpg?1670309150",
//            "name": "PET 캔시머용 페트캔 큐브 500",
//            "price": {
//              "amount": 15000
//            }
//          },
//          {
//            "id": 16,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000143.jpg?1670314117",
//            "name": "PET 캔시머용 페트캔 항아리 500",
//            "price": {
//              "amount": 15000
//            }
//          },
//          {
//            "id": 17,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000113.jpg?1606710101",
//            "name": "AL 공캔 실버 500",
//            "price": {
//              "amount": 15000
//            }
//          },
//          {
//            "id": 18,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000073.jpg?1606709934",
//            "name": "PET 원형 공캔 650",
//            "price": {
//              "amount": 16000
//            }
//          },
//          {
//            "id": 19,
//            "image": "http://modooing.co.kr/shopimages/modooin/0210010000063.jpg?1606709896",
//            "name": "PET 크리스탈 공캔 500",
//            "price": {
//              "amount": 15000
//            }
//          }
//        ]
//
//    """.trimIndent()
//
//    private fun getDisPatcher() = object : Dispatcher() {
//        override fun dispatch(request: RecordedRequest): MockResponse {
//            val path = request.path ?: MockResponse().setHeader("Content-Type", "application/json")
//                .setResponseCode(404).toString()
//            return when {
//                path.startsWith("/products/") -> {
//                    val productId = path.substringAfterLast("/")
//                    MockResponse()
//                        .setHeader("Content-Type", "application/json")
//                        .setResponseCode(200)
//                        .setBody(findById(productId))
//                }
//
//                path == "/products" ->
//                    MockResponse()
//                        .setHeader("Content-Type", "application/json")
//                        .setResponseCode(200)
//                        .setBody(products)
//
//                path.startsWith("/products") -> {
//                    val offset = path.substringAfter("offset=").substringBefore("&").toInt()
//                    val count = path.substringAfter("count=").toInt()
//                    MockResponse()
//                        .setHeader("Content-Type", "application/json")
//                        .setResponseCode(200)
//                        .setBody(getNext(offset, count))
//                }
//
//                else -> MockResponse().setResponseCode(404)
//            }
//        }
//
//        fun getUnitData(unitSize: Int, pageNumber: Int): List<Product> {
//            val tempUnit = mutableListOf<Product>()
//
//            for (index in unitSize * (pageNumber - 1) until unitSize * pageNumber) {
//                val originalIndex = index % unitSize
//                tempUnit.add(
//                    Product(
//                        id = index,
//                        imageUrl = ProductDataRepository.baseProducts[originalIndex].imageUrl,
//                        name = ProductDataRepository.baseProducts[originalIndex].name + "-$index",
//                        price = Price(ProductDataRepository.baseProducts[originalIndex].price.value),
//                    )
//                )
//            }
//            ProductDataRepository._products.addAll(tempUnit)
//            return tempUnit.toList()
//        }
//    }
// }
