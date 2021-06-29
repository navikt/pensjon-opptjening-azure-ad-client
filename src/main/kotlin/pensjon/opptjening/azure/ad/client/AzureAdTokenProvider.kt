package pensjon.opptjening.azure.ad.client

import com.microsoft.aad.msal4j.ClientCredentialParameters
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneId

private val LOG = LoggerFactory.getLogger(AzureAdTokenProvider::class.java)

class AzureAdTokenProvider(config: AzureAdEnvConfig) : TokenProvider {
    private val scopes: Set<String> = config.getScopes()
    private val confidentialClientApplication = config.getConfidentialClientApplication()
    private var cachedToken: AzureToken? = null

    override fun getToken() = refreshToken(this.cachedToken)!!.accessToken

    private fun refreshToken(azureAdToken: AzureToken?) =
        if (expiredOrNull(azureAdToken)) fetchAzureAdToken() else azureAdToken

    private fun expiredOrNull(azureAdToken: AzureToken?) = (azureAdToken == null || azureAdToken expiresWithinMinutes 2)

    private fun fetchAzureAdToken(): AzureToken {
        val clientCredentialParameters = ClientCredentialParameters.builder(scopes).build()
        val authenticationResult = confidentialClientApplication.acquireToken(clientCredentialParameters).get()
        LOG.info("Fetching new AadToken")
        return AzureToken(
            authenticationResult.accessToken(),
            authenticationResult.expiresOnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        ).also { cachedToken = it }
    }
}

internal data class AzureToken(val accessToken: String, val expires: LocalDateTime){
    internal infix fun expiresWithinMinutes(minutes: Long) = LocalDateTime.now() >= expires.minusMinutes(minutes)
}