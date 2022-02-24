<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as h>

<@c.page "${message.getTopic()}">

<p>${message.getDate()}</p>
<h2>${message.getTopic()}</h2>
<p>From:  ${message.getSender()}</p>
<p>To: ${message.getReceivers()?join(', ')}</p>
<p></p>
<p>${message.getContent()}</p>

<#foreach attach in message.getAttachments()>
    <a href="/messages/${message.getMessageID()}/download?name=${attach.getName()}&id1=${attach.getID()}">
        <i class="fa fa-download"></i>
        <span>${attach.getName()}</span>
    </a>
</#foreach>

</@c.page>