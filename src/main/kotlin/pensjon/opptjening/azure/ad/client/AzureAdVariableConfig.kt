package pensjon.opptjening.azure.ad.client

import com.microsoft.aad.msal4j.ClientCredentialFactory
import com.microsoft.aad.msal4j.ConfidentialClientApplication

class AzureAdVariableConfig(
    azureAppClientId: String,
    azureAppClientSecret: String,
    azureTargetApiId: String,
    wellKnownUrl: String,
) : AzureAdConfig() {
    private val scopes: Set<String> = setOf("api://$azureTargetApiId/.default")
    private val confidentialClientApplication: ConfidentialClientApplication = ConfidentialClientApplication
        .builder(azureAppClientId, ClientCredentialFactory.createFromSecret(azureAppClientSecret))
        .authority(wellKnownUrl)
        .build()

    override fun getScopes(): Set<String> = scopes
    override fun getConfidentialClientApplication(): ConfidentialClientApplication = confidentialClientApplication

    companion object EnvironmentKeys {
        private const val AZURE_APP_CLIENT_ID = "AZURE_APP_CLIENT_ID"
        private const val AZURE_APP_CLIENT_SECRET = "AZURE_APP_CLIENT_SECRET"
        private const val AZURE_TARGET_API_ID = "AZURE_APP_TARGET_API_ID"
        private const val AZURE_APP_WELL_KNOWN_URL = "AZURE_APP_WELL_KNOWN_URL"
    }
}