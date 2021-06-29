package pensjon.opptjening.azure.ad.client


interface TokenProvider {
    fun getToken(): String
}