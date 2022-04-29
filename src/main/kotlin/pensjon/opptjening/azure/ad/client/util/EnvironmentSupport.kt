package pensjon.opptjening.azure.ad.client.util

internal fun Map<String, String>.verifyEnvironmentVariables(vararg keys: String) {
    val missingKeys = keys.filter { this[it] == null }
    if (missingKeys.isNotEmpty()) throw MissingAzureAdEnvironmentVariables(missingKeys)
}