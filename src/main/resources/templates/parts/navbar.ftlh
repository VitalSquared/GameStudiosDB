<#include "security.ftlh">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Game Studios</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/messages/sent">Messages</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/studios">Studios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/contracts">Contracts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tests">Testings</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/games">Games</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin_panel/categories">Admin Panel</a>
                    </li>
                </#if>
                <!--<li class="nav-item">
                    <a class="nav-link" href="/userInfo">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin">Admin</a>
                </li>-->
            </#if>
        </ul>

        <#if user??>
            <a class="navbar-text mr-3" href="/userInfo">${name}</a>
            <a class="nav-link btn btn-primary" href="/logout">Log out</a>
        <#else>
            <a class="nav-link btn btn-primary" href="/login">Log in</a>
        </#if>
    </div>
</nav>