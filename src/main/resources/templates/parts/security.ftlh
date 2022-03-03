<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.getAuthorities()?sequence?seq_contains('ROLE_ADMIN'?string)
        isGeneralDirector = user.getAuthorities()?sequence?seq_contains('ROLE_GENERAL_DIRECTOR'?string)
        isStudioDirector = user.getAuthorities()?sequence?seq_contains('ROLE_STUDIO_DIRECTOR'?string)
    >
<#else>
    <#assign
        name = "unknown"
        isAdmin = false
        isGeneralDirector = false
        isStudioDirector = false
    >
</#if>

<#if isAdmin>
    <#assign
        studiosURL = '/studios'
    >
<#else>
    <#assign
        studiosURL = '/studios/my'
    >
</#if>