<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Add Genre To Gane">
    <br/>
    <form method="POST" action="/games/${gameID}/genre" enctype="multipart/form-data">
        <#assign genres_map = {}>
        <#foreach genre in genres>
            <#assign genres_map = genres_map + { genre.genreID : genre.name }>
        </#foreach>
        <label for="genreID">Genre: </label>
        <@spring.formSingleSelect "genre.genreID", genres_map/>
        <br/>
        <input type="submit" value="Add"/>
    </form>
</@c.page>