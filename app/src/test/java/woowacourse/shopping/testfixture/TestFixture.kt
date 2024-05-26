package woowacourse.shopping.testfixture

import woowacourse.shopping.data.model.ProductIdsCountData
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
