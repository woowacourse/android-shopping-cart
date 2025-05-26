package woowacourse.shopping.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCTS_1
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_1
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_2
import woowacourse.shopping.model.DUMMY_CATALOG_PRODUCT_3

class CatalogProductsTest {
    @Test
    fun `두 상품 목록을 병합하면 기존 순서를 유지하며 결합되고 hasMore는 우측 값을 따른다`() {
        // given
        val left = CatalogProducts(listOf(DUMMY_CATALOG_PRODUCT_1), hasMore = true)
        val right = CatalogProducts(listOf(DUMMY_CATALOG_PRODUCT_2), hasMore = false)

        // when
        val result = left + right

        // then
        assertThat(result.products).containsExactly(DUMMY_CATALOG_PRODUCT_1, DUMMY_CATALOG_PRODUCT_2).inOrder()
        assertThat(result.hasMore).isFalse()
    }

    @Test
    fun `상품 수량을 변경하면 해당 상품만 반영되고 나머지는 유지된다`() {
        // given
        val original = DUMMY_CATALOG_PRODUCTS_1
        val newQuantity = 100

        // when
        val updated = original.updateCatalogProductQuantity(DUMMY_CATALOG_PRODUCT_2.product.id, newQuantity)

        // then
        val modified = updated.products.first { it.product.id == DUMMY_CATALOG_PRODUCT_2.product.id }
        assertThat(modified.quantity).isEqualTo(newQuantity)

        val unmodified = updated.products.first { it.product.id == DUMMY_CATALOG_PRODUCT_1.product.id }
        assertThat(unmodified.quantity).isEqualTo(DUMMY_CATALOG_PRODUCT_1.quantity)
    }

    @Test
    fun `단일 상품 정보를 갱신하면 해당 상품만 변경된다`() {
        // given
        val original = DUMMY_CATALOG_PRODUCTS_1
        val updatedProduct = DUMMY_CATALOG_PRODUCT_2.copy(quantity = 100)

        // when
        val result = original.updateCatalogProduct(updatedProduct)

        // then
        val modified = result.products.first { it.product.id == updatedProduct.product.id }
        assertThat(modified.quantity).isEqualTo(100)

        val unmodified = result.products.first { it.product.id == DUMMY_CATALOG_PRODUCT_1.product.id }
        assertThat(unmodified.quantity).isEqualTo(DUMMY_CATALOG_PRODUCT_1.quantity)
    }

    @Test
    fun `복수 상품 정보를 갱신하면 매칭되는 항목만 반영된다`() {
        // given
        val original = DUMMY_CATALOG_PRODUCTS_1

        val updatedList =
            listOf(
                DUMMY_CATALOG_PRODUCT_1.copy(quantity = 1),
                DUMMY_CATALOG_PRODUCT_3.copy(quantity = 2),
            )

        // when
        val result = original.updateCatalogProducts(updatedList)

        // then
        assertThat(result.products.first { it.product.id == DUMMY_CATALOG_PRODUCT_1.product.id }.quantity).isEqualTo(1)
        assertThat(result.products.first { it.product.id == DUMMY_CATALOG_PRODUCT_2.product.id }.quantity)
            .isEqualTo(DUMMY_CATALOG_PRODUCT_2.quantity)
        assertThat(result.products.first { it.product.id == DUMMY_CATALOG_PRODUCT_3.product.id }.quantity).isEqualTo(2)
    }

    @Test
    fun `총 수량은 모든 상품 수량의 합과 같다`() {
        // given
        val catalogProducts =
            CatalogProducts(
                listOf(
                    DUMMY_CATALOG_PRODUCT_1.copy(quantity = 1),
                    DUMMY_CATALOG_PRODUCT_2.copy(quantity = 2),
                    DUMMY_CATALOG_PRODUCT_3.copy(quantity = 3),
                ),
                hasMore = false,
            )

        // then
        assertThat(catalogProducts.catalogProductsQuantity).isEqualTo(6)
    }
}
