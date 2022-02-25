<#macro content page url sent any_filters>
<#import "../parts/pager.ftlh" as p>
<#import "../parts/filter.ftlh" as f>

<#if page.content?size gt 0>
    <@p.pager url page/>
</#if>
<#if sent>
     <@f.filter url
        {
            'topic': ['Search topic', 'input'],
            'receiver': ['Search receiver', 'input'],
            'date' : ['Date', 'date']
        }
        {
             'topic':'topic',
             'receiver':'receiver',
             'date':'date'
        }
        any_filters!false
    />
<#else>
    <@f.filter url
        {
            'topic': ['Search topic', 'input'],
            'sender': ['Search sender', 'input'],
            'date' : ['Date', 'date'],
            'read' : ['Search read', 'boolean', '', 'Read', 'Unread']
        }
        {
            'topic':'topic',
            'sender':'sender',
            'date':'date',
            'read':'read'
        }
        any_filters!false
    />
</#if>

<#if page.content?size == 0>
    <h1>No content</h1>
</#if>

<div class="card-columns">
    <#list page.content as message>
        <div class="card
            <#if !sent>
                <#if !message.getRead()>
                    border-warning
                </#if>
            </#if>
        my-3">
            <div class="card-header">
                <h5 class="card-title
                    <#if !sent>
                        <#if !message.getRead()>
                            border-warning
                            text-warning
                        </#if>
                    </#if>
                ">
                    ${message.getTopic()}
                    <#if !sent>
                        <#if !message.getRead()>
                            <span class="badge badge-secondary">New</span>
                        </#if>
                    </#if>
                </h5>
                <#if !sent>
                    From: ${message.getSender()}
                    <br/>
                </#if>
                <#if message.getReceivers()?size gt 0>
                    To: ${message.getReceivers()?join(', ')?truncate(100, '...')}
                </#if>
            </div>
            <div class="card-body">
                <p class="card-text">
                    ${message.getContent()?truncate(100, '...')}
                    <br/>
                </p>
                <ul class="list-group list-group-flush">
                    <#if message.getAttachments()?size gt 0>
                        <li class="list-group-item">
                            ${message.getAttachments()[0].getName()}
                        </li>
                    </#if>
                    <#if message.getAttachments()?size gt 1>
                        <li class="list-group-item">
                            ${message.getAttachments()[1].getName()}
                        </li>
                    </#if>
                    <#if message.getAttachments()?size gt 2>
                        <li class="list-group-item">
                            (+${message.getAttachments()?size - 2} attachments)
                        </li>
                    </#if>
                </ul>
                <br/>
            </div>
            <div class="card-footer text-muted">
                ${message.getDate()?datetime}
                <br/>
                <br/>
                <a href="/messages/${message.getMessageID()}" class="btn btn-primary">View</a>
                <#if sent>
                    <#if message.getReceivers()?size = 0>
                        <a href="/messages/delete_sent/${message.getMessageID()}" class="btn btn-primary">Delete</a>
                    </#if>
                <#else>
                    <a href="/messages/delete_recv/${message.getMessageID()}" class="btn btn-primary">Delete</a>
                </#if>
            </div>
        </div>
    </#list>
</div>

<#if page.content?size gt 0>
    <@p.pager url page/>
</#if>

</#macro>