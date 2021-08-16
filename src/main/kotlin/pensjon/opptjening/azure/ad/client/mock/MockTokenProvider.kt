package pensjon.opptjening.azure.ad.client.mock

import pensjon.opptjening.azure.ad.client.TokenProvider

class MockTokenProvider(private val mockTokenContent: String = "alg.content.digs") : TokenProvider {
    override fun getToken() = mockTokenContent
}