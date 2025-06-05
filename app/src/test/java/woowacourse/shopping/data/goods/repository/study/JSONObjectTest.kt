package woowacourse.shopping.data.goods.repository.study

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.Test

class JSONObjectTest {
    @Test
    fun `빈 JSON 객체는 {} 문자열로 변환된다`() {
        val expected = JSONObject()
        val actual =
            JSONObject(
                """
                {}
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }

    @Test
    fun `문자열 값을 put 하면 JSON 문자열에 포함된다`() {
        val expected =
            JSONObject().apply {
                put("key", "value")
            }
        val actual =
            JSONObject(
                """
                {
                 "key":"value"   
                }
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }

    @Test
    fun `정수 값을 put 하면 JSON 문자열에 포함된다`() {
        val expected =
            JSONObject().apply {
                put("key", 1)
            }
        val actual =
            JSONObject(
                """
                {
                       "key":1
                }
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }

    @Test
    fun `불리언 값을 put 하면 JSON 문자열에 포함된다`() {
        val expected =
            JSONObject().apply {
                put("key", true)
            }
        val actual =
            JSONObject(
                """
                {
                    "key":true
                }
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }

    @Test
    fun `실수 값을 put 하면 JSON 문자열에 포함된다`() {
        val expected =
            JSONObject().apply {
                put("key", 1.0)
            }
        val actual =
            JSONObject(
                """
                {
                    "key":1.0
                }
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }

    @Test
    fun `중첩 객체를 put 하면 JSON 구조에 포함된다`() {
        val expected =
            JSONObject().apply {
                put(
                    "key",
                    JSONObject().apply {
                        put("key", "value")
                    },
                )
            }
        val actual =
            JSONObject(
                """
                {
                    "key":{"key":"value"}
                }
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }

    @Test
    fun `리스트 값을 put 하면 JSON 배열로 변환된다`() {
        val expected =
            JSONObject().apply {
                put("key", listOf("value1", "value2"))
            }
        val actual =
            JSONObject(
                """
                {
                    "key":["value1","value2"]
                }
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }

    @Test
    fun `객체와 배열이 중첩된 구조도 JSON으로 변환된다`() {
        val expected =
            JSONObject().apply {
                put(
                    "key",
                    JSONObject().apply {
                        put("key", listOf("value1", "value2"))
                    },
                )
            }
        val actual =
            JSONObject(
                """
                {
                    "key":{key:["value1","value2"]}
                }
                """.trimIndent(),
            )
        assertThat(actual.toString())
            .isEqualTo(expected.toString())
    }
}
