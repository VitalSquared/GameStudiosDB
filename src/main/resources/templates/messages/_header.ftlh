<#macro header numberOfUnread>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/messages/sent">Sent messages</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/messages/received">
                        <#if numberOfUnread == 0>
                            Received messages
                        <#else>
                            Received messages (${numberOfUnread})
                        </#if>
                    </a>
                </li>
            </ul>

            <a class="nav-link btn btn-primary" href="/messages/new_message">New Message</a>
        </div>
    </nav>
</#macro>