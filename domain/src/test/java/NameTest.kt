import model.Name
import org.junit.Test

class NameTest {

    @Test(expected = IllegalArgumentException::class)
    fun `이름이 공백인경우 예외가 발생한다`() {
        Name("")
    }

    @Test
    fun `이름이 공백이 아닌 경우 예외가 발생하지 않는다`() {
        Name("woogi")
    }
}
