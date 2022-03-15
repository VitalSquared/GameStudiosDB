<#import "parts/common.ftlh" as c>

<@c.page "Not allowed">
  <#if errorMsg??>
    <h3 style="color: red;">${errorMsg}</h3>
  </#if>
</@c.page>