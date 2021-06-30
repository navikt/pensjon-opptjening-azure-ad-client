package pensjon.opptjening.azure.ad.client

import com.microsoft.aad.msal4j.ConfidentialClientApplication

abstract class AzureAdConfig {
    internal abstract fun getScopes(): Set<String>
    internal abstract fun getConfidentialClientApplication(): ConfidentialClientApplication
}