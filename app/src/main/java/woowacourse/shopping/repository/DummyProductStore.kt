package woowacourse.shopping.repository

import woowacourse.shopping.db.Product

class DummyProductStore : ProductStore {
    private val products =
        List(10000) { i ->
            Product(
                i,
                "https://s3-alpha-sig.figma.com/img/05ef/e578/+" +
                    "d81445480aff1872344a6b1b35323488?Expires=1716768000&+" +
                    "Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4+" +
                    "&Signature=M4f1eRJD9cBxm2tCvzA2tg0dDm63Zu22+" +
                    "-WK8EtTWXDnemL9qYi0DzipwbS-wx8tqy+" +
                    "~fHLbxWUGQnA9zFlcQAG9NTxKjhu55XgvQHfdQOlXGik1GMJKIINPKGTq09iIfG+" +
                    "-xM0Cs~4il-E8C3M7wFcfky5PqicFvNhedNCtMt7orA+" +
                    "-sbe2us5qJEBiRnzUawFyqXliyo-NeqOCke87O-nmNMKsEM70x+" +
                    "-9gsaFoMfPWXfJE95tSaWU4WVgqRzK91WYo2TivZ2gw68PSajjTTjrJsh0v89nUZ9ZyrNK+" +
                    "~TyqdXXSCKA2uGes5t9Eh6I4k59rjUfDtfFv46yo5cNsxfEjEFg__",
                "$i 번째 상품 이름",
                i * 100,
            )
        }

    override fun loadDataAsNeeded(currentIndex: Int): List<Product> {
        return products.subList(currentIndex, currentIndex + COUNT_PER_LOAD)
    }

    override fun findById(findId: Int): Product {
        return products.find { it.id == findId } ?: throw IllegalArgumentException("해당하는 상품이 없습니다.")
    }

    companion object {
        private const val COUNT_PER_LOAD = 20
    }
}
