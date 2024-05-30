package woowacourse.shopping.testfixture

import woowacourse.shopping.data.model.ProductIdsCountData
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductIdsCount

/**
 * @param dataCount 생성할 ProductIdsCountData 의 개수
 * @param quantity ProductIdsCountData 의 수량. 기본값은 1
 * @return 개수가 1 인 ProductIdsCountData 리스트를 생성한다
 */
fun productsIdCountDataTestFixture(
    dataCount: Int,
    quantity: Int = 1,
): List<ProductIdsCountData> =
    List(dataCount) {
        ProductIdsCountData(it.toLong(), quantity)
    }

/**
 * @param dataCount 생성할 ProductIdsCount 의 데이터 개수
 * @param quantity ProductIdsCount 의 수량. 기본값은 1
 * @return 개수가 1 인 ProductIdsCount 리스트를 생성한다
 */
fun productsIdCountTestFixture(
    dataCount: Int,
    quantity: Int = 1,
): List<ProductIdsCount> =
    List(dataCount) {
        ProductIdsCount(it.toLong(), quantity)
    }

/**
 * @param id Product 의 id
 * @param name Product 의 이름. 기본값은 "$id name"
 * @param 나머지 파라미도 직접 지정 가능하지만 기본값이 지정되어 있다
 * @return Product 객체를 생성한다
 */
fun productDomainTestFixture(
    id: Long,
    name: String = "$id name",
    imageUrl: String = "1",
    price: Int = 1,
    quantity: Int = 0
): Product = Product(
    id = id,
    name = name,
    imgUrl = imageUrl,
    price = price,
    quantity = quantity
)

/**
 * @param dataCount 생성할 Product 의 데이터 개수
 * @param productDomainTestFixture Product 의 데이터를 생성하는 함수
 * @return Product 리스트를 생성한다
 */
fun productDomainsTestFixture(
    dataCount: Int,
    productDomainTestFixture: (Int) -> Product = { productDomainTestFixture(it.toLong()) },
): List<Product> = List(dataCount) {
    productDomainTestFixture(it)
}
