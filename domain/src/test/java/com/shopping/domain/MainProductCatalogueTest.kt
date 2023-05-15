package com.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MainProductCatalogueTest {

    @Test
    internal fun `컬렉션의 크기를 반환한다`() {
        // given
        val products = listOf(Product("", "", 1), Product("", "", 2))
        val mainProductCatalogue = MainProductCatalogue(products)

        // when
        val actual = mainProductCatalogue.size

        // then
        assertThat(actual).isEqualTo(2)
    }

    @Test
    internal fun `데이터를 한번에 추가할 수 있다`() {
        // given
        val mainProductCatalogue = MainProductCatalogue()
        val products = listOf(Product("", "", 1), Product("", "", 2))

        // when
        val actual = mainProductCatalogue.addAll(products)

        // then
        assertThat(actual.size).isEqualTo(2)
    }

    @Test
    internal fun `찾으려는 값이 있다면 true를 반환한다`() {
        // given
        val products = listOf(Product("", "", 1), Product("", "", 2))
        val mainProductCatalogue = MainProductCatalogue(products)

        // when
        val actual = mainProductCatalogue.contains(Product("", "", 1))

        // then
        assertThat(actual).isTrue
    }

    @Test
    internal fun `값을 추가할 수 있다`() {
        // given
        val products = listOf(Product("", "", 1))
        val mainProductCatalogue = MainProductCatalogue(products)

        // when
        val actual = mainProductCatalogue.add(Product("", "", 2))

        // then
        assertThat(actual.contains(Product("", "", 2))).isTrue
    }

    @Test
    internal fun `값을 제거할 수 있다`() {
        // given
        val products = listOf(Product("", "", 1), Product("", "", 2))
        val mainProductCatalogue = MainProductCatalogue(products)

        // when
        val actual = mainProductCatalogue.remove(Product("", "", 2))

        // then
        assertThat(actual.contains(Product("", "", 2))).isFalse
    }

    @Test
    internal fun `첫번째 값을 제거할 수 있다`() {
        // given
        val products = listOf(Product("", "", 1), Product("", "", 2))
        val mainProductCatalogue = MainProductCatalogue(products)

        // when
        val actual = mainProductCatalogue.removeFirst()

        // then
        assertThat(actual.contains(Product("", "", 1))).isFalse
    }

    @Test
    internal fun `첫번째 값을 제거할때 첫번째 값이 존재하지 않는다면 그대로 반환한다`() {
        // given
        val mainProductCatalogue = MainProductCatalogue()

        // when
        val actual = mainProductCatalogue.removeFirst()

        // then
        assertThat(actual.size).isEqualTo(0)
    }
}
