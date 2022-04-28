package pensjon.opptjening.azure.ad.client

import com.microsoft.aad.msal4j.ClientCredentialFactory
import com.microsoft.aad.msal4j.ConfidentialClientApplication
import pensjon.opptjening.azure.ad.client.AzureAdVariableConfig.EnvironmentKeys.AZURE_APP_CLIENT_ID
import pensjon.opptjening.azure.ad.client.AzureAdVariableConfig.EnvironmentKeys.AZURE_APP_CLIENT_SECRET
import pensjon.opptjening.azure.ad.client.AzureAdVariableConfig.EnvironmentKeys.AZURE_APP_WELL_KNOWN_URL
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URL

/**
 * @param azureAppClientId your apps Azure Client id. Env variable key [AZURE_APP_CLIENT_ID]
 * @param azureAppClientSecret your apps Azure Client secret. Env variable key [AZURE_APP_CLIENT_SECRET]
 * @param wellKnownUrl well known endpoint for azure ad issuer. Env variable key [AZURE_APP_WELL_KNOWN_URL]
 * @param targetApiId <cluster>.<namespace>.<app-name>
 */
class AzureAdVariableConfig(
    azureAppClientId: String,
    azureAppClientSecret: String,
    wellKnownUrl: String,
    targetApiId: String,
    proxyUrl: URL? = null,
) : AzureAdConfig() {
    private val scopes: Set<String> = setOf("api://$targetApiId/.default")
    private val confidentialClientApplication: ConfidentialClientApplication = ConfidentialClientApplication
        .builder(azureAppClientId, ClientCredentialFactory.createFromSecret(azureAppClientSecret))
        .authority(wellKnownUrl)
        .apply { if (proxyUrl != null) proxy(createProxy(proxyUrl)) }
        .build()

    override fun getScopes(): Set<String> = scopes
    override fun getConfidentialClientApplication(): ConfidentialClientApplication = confidentialClientApplication

    companion object EnvironmentKeys {
        const val AZURE_APP_CLIENT_ID = "AZURE_APP_CLIENT_ID"
        const val AZURE_APP_CLIENT_SECRET = "AZURE_APP_CLIENT_SECRET"
        const val AZURE_APP_WELL_KNOWN_URL = "AZURE_APP_WELL_KNOWN_URL"
    }

    private fun createProxy(proxyUrl: URL) = Proxy(Proxy.Type.HTTP, InetSocketAddress(proxyUrl.host, proxyUrl.port))
}