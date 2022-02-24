<#import "../parts/common.ftlh" as c>
<#import "message_cards.ftlh" as card>

<@c.page "Sent messages">
    <#include "_header.ftlh">
    <@card.content page url true/>
</@c.page>