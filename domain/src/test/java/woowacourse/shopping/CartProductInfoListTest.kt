package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartProductInfoListTest {
    @Test
    fun `Count가 1 인 CartProductInfo를 6개 들고있다면, count 는 6이다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 6)
        // when
        val actual = cartProductInfoList.count
        // then
        val expected = 6
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `아이디가 다른 CartProduct를 추가할 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 3)
        // when
        val actual = cartProductInfoList.add(CartProductInfo(makeTestProduct(5), 1)).size
        // then
        assertThat(actual).isEqualTo(4)
    }

    @Test
    fun `아이디가 같은 CartProduct를 추가할 수 없다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 3)
        // when
        val actual = cartProductInfoList.add(CartProductInfo(makeTestProduct(3), 1)).size
        // then
        assertThat(actual).isEqualTo(3)
    }

    @Test
    fun `아이디가 다른 CartProductList의 아이템들을 추가할 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 2)
        val newCartProductInfoList = makeTestCartProductInfoList(3, 2)
        // when
        val actual = cartProductInfoList.addAll(newCartProductInfoList.items).size
        // then
        assertThat(actual).isEqualTo(4)
    }

    @Test
    fun `아이디가 같은 상품정보를 제거할 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 1)
        // when
        val actual = cartProductInfoList.delete(CartProductInfo(makeTestProduct(1), 1))
        // then
        assertThat(actual.size).isEqualTo(0)
    }

    @Test
    fun `비용이 1000원인 상품이 6개 있을때, 전체 가격은 6000원이다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 6)
        // when
        val actual = cartProductInfoList.totalPrice
        // then
        assertThat(actual).isEqualTo(6000)
    }

    @Test
    fun `특정 인덱스의 주문 상태를 바꿀 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 3)
        // when
        val actual =
            cartProductInfoList.updateItemOrdered(0, true)
        // then
        assertThat(actual.items[0].isOrdered).isTrue
    }

    @Test
    fun `모든 상품의 주문 상태를 바꿀 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 3)
        // when
        val actual = cartProductInfoList.updateAllItemOrdered(true)
        // then
        assertThat(actual.items[0].isOrdered).isTrue
        assertThat(actual.items[2].isOrdered).isTrue
    }

    @Test
    fun `특정 인덱스 상품의 개수를 바꿀 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 3)
        // when
        val actual = cartProductInfoList.updateItemCount(0, 3)
        // then
        val expected = 3
        assertThat(actual.items[0].count).isEqualTo(expected)
    }

    @Test
    fun `시작지점부터 크기만큼의 CartProductList를 반환할 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 8)
        // when
        val actual = cartProductInfoList.getItemsInRange(startIndex = 0, size = 3)
        // then
        assertThat(actual.size).isEqualTo(3)
        assertThat(actual.items.first().product.id).isEqualTo(1)
        assertThat(actual.items.last().product.id).isEqualTo(3)
    }

    @Test
    fun `아이디가 같은 상품이 있다면, 그 상품 정보를 새로운 상품정보로 교체한다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 8)
        val newItem = CartProductInfo(makeTestProduct(1), 2)
        // when
        val actual = cartProductInfoList.replaceItem(newItem).items[0]
        // then
        assertThat(actual).isEqualTo(newItem)
    }

    @Test
    fun `여러개의 상품 정보를 교체할 수 있다`() {
        // given
        val cartProductInfoList = makeTestCartProductInfoList(1, 8)
        val newItemList = CartProductInfoList(
            listOf(
                CartProductInfo(makeTestProduct(1), 2),
                CartProductInfo(makeTestProduct(2), 2),
                CartProductInfo(makeTestProduct(3), 2),
            ),
        )
        // when
        val actual = cartProductInfoList.replaceItemList(newItemList)
        // then
        assertThat(actual.items.first().count).isEqualTo(2)
        assertThat(actual.items[2].count).isEqualTo(2)
    }

    private fun makeTestProduct(id: Int): Product = Product(id, "", "", Price(1000))
    private fun makeTestCartProductInfoList(startId: Int, size: Int): CartProductInfoList =
        CartProductInfoList(
            List(size) { CartProductInfo(makeTestProduct(it + startId), 1) },
        )
}
