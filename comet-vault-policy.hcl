# ==============================================================================
# auris Application Vault Policy
# ==============================================================================

# 1. Allow reading the system health check
path "sys/health" {
  capabilities = ["read", "list"]
}

# 2. Allow full control over the application's specific transit key
path "transit/keys/auris-oauth-transit-key" {
  capabilities = ["create", "read", "update"]
}

# 2a. Allow read/list of all transit keys metadata (needed for health check)
path "transit/keys/*" {
  capabilities = ["read", "list"]
}

# 3. Allow the application to use the transit key to sign JWTs
path "transit/sign/auris-oauth-transit-key" {
  capabilities = ["update"]
}
