package pensjon.opptjening.azure.ad.client

internal fun Map<String, String>.verifyEnvironmentVariables(vararg keys: String) {
    val missingKeys = keys.filter { this[it] == null }
    if (missingKeys.isNotEmpty()) throw MissingAzureAdEnvironmentVariables(missingKeys)
}

class MissingAzureAdEnvironmentVariables(missingKeys: List<String>)
    : RuntimeException(missingKeys.joinToString(", ", "Missing azure ad env keys: "))