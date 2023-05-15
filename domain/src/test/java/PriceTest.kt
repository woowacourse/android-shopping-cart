import model.Price
import org.junit.Test

class PriceTest {

    @Test(expected = IllegalArgumentException::class)
    fun `가격이 0원보다 작은 경우 예외가 발생한다`() {
        Price(0)
    }

    @Test
    fun `가격이 0원보다 큰 경우 예외가 발생하지 않는다`() {
        Price(100)
    }
}
