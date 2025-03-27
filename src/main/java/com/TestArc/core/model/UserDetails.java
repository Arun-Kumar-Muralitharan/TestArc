package com.TestArc.core.model;

public class UserDetails
{
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String username;
    private String password;
    private String scope;
    private String bearerToken;
    private String mfaToken;
    private String entityId;


    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public void setGrantType(String grantType)
    {
        this.grantType = grantType;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public String getBearerToken()
    {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken)
    {
        this.bearerToken = bearerToken;
    }

    public String getMfaToken()
    {
        return mfaToken;
    }

    public void setMfaToken(String mfaToken)
    {
        this.mfaToken = mfaToken;
    }

    public String getEntityId()
    {
        return entityId;
    }

    public void setEntityId(String entityId)
    {
        this.entityId = entityId;
    }
}
