package pensjon.opptjening.azure.ad.client

class MissingAzureAdEnvironmentVariables(missingKeys: List<String>) : RuntimeException(missingKeys.joinToString(", ", "Missing azure ad env keys: "))