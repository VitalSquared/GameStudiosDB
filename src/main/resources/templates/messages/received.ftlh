<#import "../parts/common.ftlh" as c>
<#import "message_cards.ftlh" as card>
<#import "_header.ftlh" as header>

<@c.page "Received messages">
    <@header.header numberOfUnread/>
    <@card.content page url false filters/>
</@c.page>