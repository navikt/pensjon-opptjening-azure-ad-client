# pensjon-opptjening-azure-ad-client
Common functionality for retrieving azure-ad token from gcp.

# How it works
### AzureAdTokenProvider
AzureAdTokenProvider cashes and retrieves token for you.
AzureAdTokenProvider require a AzureAdConfig to properly communicate with the tenant.

### AzureAdEnvConfig
AzureAdEnvConfig retrieves the necessary information from the environment. 
Use azure and accessPolicy in the yaml file to mount the environment variables needed (https://doc.nais.io/security/auth/azure-ad/).

# Future work 
1) Make a springboot compatible version of AzureAdConfig.
2) Handle more than one scope.
3) Make more and better tests.