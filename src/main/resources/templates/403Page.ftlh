<#import "parts/common.ftlh" as c>

<@c.page "Not allowed">
  <#if message??>
    <h3 style="color: red;">${message}</h3>
  </#if>
  <#if userInfo??>
    <div>${userInfo}</div>
  </#if>
</@c.page>