<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Add Genre To Gane">
    <br/>
    <form method="POST" action="/games/${gameID}/genre" enctype="multipart/form-data">
        <#assign genres_map = {}>
        <#foreach genre in genres>
            <#assign genres_map = genres_map + { genre.genreID : genre.name }>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="genreID" style="width: 100px;">Genre: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "genre.genreID", genres_map, "class=\"form-control\""/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Add"/>
    </form>
</@c.page>