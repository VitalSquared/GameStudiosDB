<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>

<@c.page "User Info">

<h2>User Info Page</h2>
<h3>Welcome : ${s.name}</h3>
<b>This is protected page!</b>

<br/><br/>
<#if userInfo??>
    <div>${userInfo}</div>
</#if>
</@c.page>