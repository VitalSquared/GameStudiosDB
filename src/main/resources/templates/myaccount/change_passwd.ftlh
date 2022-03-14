<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Change Password">
    <br/>
    <form method="POST" action="/userInfo/change_password" enctype="multipart/form-data">
        <label for="topic">Old Password: </label>
        <@spring.formPasswordInput "password.oldPassword"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="content">New Password: </label>
        <@spring.formPasswordInput "password.newPassword"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="content">Repeat New Password: </label>
        <@spring.formPasswordInput "password.newPasswordRepeat"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>

        <input type="submit" value="Change"/>
    </form>
</@c.page>