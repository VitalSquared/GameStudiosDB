<#import "../parts/common.ftlh" as c>

<@c.page "Sent messages">
    <#include "_header.ftlh">
    <#foreach message in messages>
        <tr class="tableBody" >
            <td>
                <#if message.getReceivers()??>
                    <#if message.getReceivers()?size gt 0>
                        <span>${message.getReceivers()[0]}</span>
                    </#if>
                    <#if (message.getReceivers()?size gt 1)>
                        <span>, ...</span>
                    </#if>
                </#if>
            </td>
            <td>
                <a href="/messages/${message.getMessageID()}">
                    ${message.getTopic()}
                </a>
            </td>
            <td>${message.getDate()?datetime}</td>
            <td>
                <#if message.getReceivers()??>
                    <a href="/messages/delete_sent/${message.getMessageID()}">
                        delete
                    </a>
                </#if>
            </td>
        </tr>
    </#foreach>
</@c.page>