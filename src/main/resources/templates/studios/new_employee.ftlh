<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "New Employee">
    <@header.header/>
    <br/>
    <form method="POST" action="/employees/new" enctype="multipart/form-data">
        <label for="first_name">First Name: </label>
        <@spring.formInput "employee.firstName"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="last_name">Last Name: </label>
        <@spring.formInput "employee.lastName"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <label for="birth_date">Birth Date: </label>
        <label for="birthday">
            <@spring.bind "employee.birthDate"/>
            <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(employee.birthDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
            <@spring.showErrors "<br>" "color:red;"/>
        </label>
        <br/>
        <#assign studios_map = {}>
        <#foreach studio in studios>
            <#assign studios_map = studios_map + { studio.studioID : studio.name }>
        </#foreach>
        <label for="studio">Studio: </label>
        <@spring.formSingleSelect "employee.studioID", studios_map "class=\"studioSelect\""/>
        <br/>
        <#assign employee_type_map = { "developer" : "Developer" , "director" : "Director" }>
        <label for="employeeType">Employee Type: </label>
        <@spring.formSingleSelect "employee.employeeType", employee_type_map, "class=\"employeeTypeSelect\""/>
        <br/>

        <#assign departments_map = {}>
        <#foreach department in departments>
            <#assign departments_map = departments_map + { department.departmentID : [department.name, department.studioID] }
            >
        </#foreach>

        <#assign categories_map = {}>
        <#foreach category in categories>
            <#assign categories_map = categories_map + { category.categoryID : category.name }>
        </#foreach>
        <div class="employeeTypeDisable">
             <label for="department" class="departmentDisable">Department: </label>
            <@formSingleSelect "employee.departmentID", departments_map,  "class=\"departmentSelectDisable\""/>
            <br/>
        </div>
        <div class="employeeTypeDisable">
            <label for="category">Category: </label>
            <@spring.formSingleSelect "employee.categoryID", categories_map/>
            <br/>
        </div>

        <label for="first_name">Email: </label>
        <@spring.formInput "account.email"/>
        <@spring.showErrors "<br>" "color:red;"/>
        <br/>
        <#assign actives_map = {"true" : "Active", "false" : "Inactive"}>
        <label for="active">Active: </label>
        <@spring.formSingleSelect "employee.active", actives_map/>
        <br/>
        <input type="submit" value="Create"/>
    </form>
</@c.page>

<#macro formSingleSelect path options attributes="">
    <@spring.bind path/>
    <select id="${spring.status.expression?replace('[','')?replace(']','')}" name="${spring.status.expression}" ${attributes?no_esc}>
        <#if options?is_hash>
            <#list options?keys as value>
                <option value="${value}"<@spring.checkSelected value/> data-studio="${options[value][1]}">${options[value][0]}</option>
            </#list>
        </#if>
    </select>
</#macro>

<script>
    $('select.employeeTypeSelect').on('change', function() {
        console.log(this.value)
        if (this.value === "developer") {
            $('div.employeeTypeDisable').show();
        }
        else {
            $('div.employeeTypeDisable').hide();
        }
    });
    $('select.studioSelect').on('change', function() {
        let studio = this.value;
        $('#departmentID option').map(function () {
            if (this.getAttribute("data-studio") === studio) {
                $(this).show();
                $(this).prop("disable", false);
            }
            else {
                $(this).hide();
                $(this).prop("disable", true);
            }
        })
    });
</script>