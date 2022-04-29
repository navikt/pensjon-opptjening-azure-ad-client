package pensjon.opptjening.azure.ad.client.util

class MissingAzureAdEnvironmentVariables(missingKeys: List<String>) : RuntimeException(missingKeys.joinToString(", ", "Missing azure ad env keys: "))