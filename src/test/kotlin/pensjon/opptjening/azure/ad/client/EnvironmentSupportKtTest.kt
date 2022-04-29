package pensjon.opptjening.azure.ad.client


import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pensjon.opptjening.azure.ad.client.util.MissingAzureAdEnvironmentVariables
import pensjon.opptjening.azure.ad.client.util.verifyEnvironmentVariables

internal class EnvironmentSupportKtTest {

    @Test
    fun `should throw MissingAzureAdEnvironmentVariables when env varibales are not found`() {
        val notFound = "NotFound"
        val exception: MissingAzureAdEnvironmentVariables = assertThrows {
            mapOf("TestKey" to "TestValue").verifyEnvironmentVariables("TestKey", notFound)
        }
        assertTrue(exception.message!!.contains(notFound))
    }
}