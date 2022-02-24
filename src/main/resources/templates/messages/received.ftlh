<#import "../parts/common.ftlh" as c>

<@c.page "Received messages">
    <#include "_header.ftlh">
    <#foreach message in messages>
        <tr class="tableBody" >
            <td>${message.getSender()}</td>
            <td>
                <a href="/messages/${message.getMessageID()}">
                    ${message.getTopic()}
                </a>
            </td>
            <td>${message.getDate()?datetime}</td>
            <td>
                <a href="/messages/delete_recv/${message.getMessageID()}">
                    delete
                </a>
            </td>
        </tr>
    </#foreach>
</@c.page>