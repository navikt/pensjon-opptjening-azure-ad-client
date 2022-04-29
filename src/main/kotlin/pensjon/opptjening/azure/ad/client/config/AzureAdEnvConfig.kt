package pensjon.opptjening.azure.ad.client.config

import com.microsoft.aad.msal4j.ClientCredentialFactory
import com.microsoft.aad.msal4j.ConfidentialClientApplication
import pensjon.opptjening.azure.ad.client.util.verifyEnvironmentVariables

class AzureAdEnvConfig(environment: Map<String, String>) : AzureAdConfig() {
    private val scopes: Set<String>
    private val confidentialClientApplication: ConfidentialClientApplication

    init {
        environment.verifyEnvironmentVariables(CLIENT_ID, CLIENT_PASSWORD, TARGET_API_ID, WELL_KNOWN_URL)
        scopes = setOf("api://${environment[TARGET_API_ID]!!}/.default")
        confidentialClientApplication = createConfidentialClientApplication(environment)
    }

    override fun getScopes(): Set<String> = scopes
    override fun getConfidentialClientApplication(): ConfidentialClientApplication = confidentialClientApplication

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