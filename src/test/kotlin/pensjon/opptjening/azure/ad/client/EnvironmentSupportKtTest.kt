package pensjon.opptjening.azure.ad.client


import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class EnvironmentSupportKtTest {

    @Test
    fun `should throw MissingAzureAdEnvironmentVariables when env varibales are not found`() {
        val notFound = "NotFound"
        val exception = assertThrows<MissingAzureAdEnvironmentVariables> {
            mapOf("TestKey" to "TestValue").verifyEnvironmentVariables("TestKey", notFound)
        }
        assertTrue(exception.message!!.contains(notFound))
    }
}