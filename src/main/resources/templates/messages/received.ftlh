<#import "../parts/common.ftlh" as c>
<#import "message_cards.ftlh" as card>

<@c.page "Received messages">
    <#include "_header.ftlh">
    <@card.content page url false/>
</@c.page>