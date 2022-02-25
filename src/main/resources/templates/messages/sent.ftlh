<#import "../parts/common.ftlh" as c>
<#import "message_cards.ftlh" as card>
<#import "_header.ftlh" as header>

<@c.page "Sent messages">
    <@header.header numberOfUnread/>
    <@card.content page url true anyFilters/>
</@c.page>