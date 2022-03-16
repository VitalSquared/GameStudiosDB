<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Change Password">
    <br/>
    <form method="POST" action="/userInfo/change_password" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="topic" style="width: 100px;">Old Password: </label>
            <div class="col-sm-5">
                <@spring.formPasswordInput "password.oldPassword", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="content" style="width: 100px;">New Password: </label>
            <div class="col-sm-5">
                <@spring.formPasswordInput "password.newPassword", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="content" style="width: 100px;">Repeat New Password: </label>
            <div class="col-sm-5">
                <@spring.formPasswordInput "password.newPasswordRepeat", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Change"/>
    </form>
</@c.page>