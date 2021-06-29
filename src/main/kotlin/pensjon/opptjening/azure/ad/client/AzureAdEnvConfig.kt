package pensjon.opptjening.azure.ad.client

import com.microsoft.aad.msal4j.ClientCredentialFactory
import com.microsoft.aad.msal4j.ConfidentialClientApplication

class AzureAdEnvConfig(environment: Map<String, String>) {
    private val scopes: Set<String>
    private val confidentialClientApplication: ConfidentialClientApplication

    init {
        environment.verifyEnvironmentVariables(CLIENT_ID, CLIENT_PASSWORD, TARGET_API_ID, WELL_KNOWN_URL)
        scopes = setOf("api://${environment[TARGET_API_ID]!!}/.default")
        confidentialClientApplication = createConfidentialClientApplication(environment)
    }

    internal fun getScopes() = scopes
    internal fun getConfidentialClientApplication() = confidentialClientApplication

    private fun createConfidentialClientApplication(environment: Map<String, String>): ConfidentialClientApplication {
        val clientId = environment[CLIENT_ID]!!
        val clientPassword = environment[CLIENT_PASSWORD]!!
        val clientSecret = ClientCredentialFactory.createFromSecret(clientPassword)
        val authorityUrl = environment[WELL_KNOWN_URL]!!
        return ConfidentialClientApplication
            .builder(clientId, clientSecret)
            .authority(authorityUrl)
            .build()
    }

    companion object EnvironmentKeys {
        private const val CLIENT_ID = "AZURE_APP_CLIENT_ID"
        private const val CLIENT_PASSWORD = "AZURE_APP_CLIENT_SECRET"
        private const val TARGET_API_ID = "AZURE_APP_TARGET_API_ID"
        private const val WELL_KNOWN_URL = "AZURE_APP_WELL_KNOWN_URL"
    }
}