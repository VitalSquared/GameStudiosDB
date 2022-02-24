<#macro content page url sent>
<#import "../parts/pager.ftlh" as p>

<@p.pager url page/>

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
                    <#foreach attach in message.getAttachments()>
                        <li class="list-group-item">
                            ${attach.getName()}
                        </li>
                    </#foreach>
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

<@p.pager url page/>

</#macro>