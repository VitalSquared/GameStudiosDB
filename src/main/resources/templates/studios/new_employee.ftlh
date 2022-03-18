<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "/spring.ftl" as spring />

<@c.page "New Employee">
    <@header.header/>
    <br/>
    <form method="POST" action="/employees/new" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="first_name" style="width: 100px;">First Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "employee.firstName", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="last_name" style="width: 100px;">Last Name: </label>
            <div class="col-sm-5">
                <@spring.formInput "employee.lastName", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="birth_date" style="width: 100px;">Birth Date: </label>
            <div class="col-sm-5">
                <label for="birthday">
                    <@spring.bind "employee.birthDate"/>
                    <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(employee.birthDate!.now)?string('YYYY-MM-dd')}" placeholder="" />
                    <@spring.showErrors "<br>" "color:red;"/>
                </label>
            </div>
        </div>

        <#if studios?sequence?size == 1>
            <#assign studios_map = {}>
        <#else>
            <#assign studios_map = { -1 : 'Select Studio...' }>
        </#if>

        <#foreach studio in studios>
            <#assign studios_map = studios_map + { studio.studioID : studio.name }>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="studio" style="width: 100px;">Studio: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "employee.studioID", studios_map "class=\"studioSelect form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <#assign employee_type_map = { "developer" : "Developer" , "director" : "Director" }>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="employeeType" style="width: 100px;">Employee Type: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "employee.employeeType", employee_type_map, "class=\"employeeTypeSelect form-control\""/>
            </div>
        </div>

        <#assign departments_map = {}>
        <#foreach department in departments>
            <#assign departments_map = departments_map + { department.departmentID : [department.name, department.studioID] }
            >
        </#foreach>

        <#assign categories_map = {}>
        <#foreach category in categories>
            <#assign categories_map = categories_map + { category.categoryID : category.name }>
        </#foreach>

        <div class="form-group row offset-1 employeeTypeDisable" style="align-items: center;">
            <label for="department" class="departmentDisable" style="width: 100px;">Department: </label>
            <div class="col-sm-5">
                <@formSingleSelect "employee.departmentID", departments_map,  "class=\"departmentSelectDisable form-control\""/>
            </div>
        </div>

        <div class="form-group row offset-1 employeeTypeDisable" style="align-items: center;">
            <label for="category" style="width: 100px;">Category: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "employee.categoryID", categories_map, "class=\"form-control\""/>
            </div>
        </div>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="first_name" style="width: 100px;">Email: </label>
            <div class="col-sm-5">
                <@spring.formInput "account.email", "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <#assign actives_map = {"true" : "Active", "false" : "Inactive"}>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="active" style="width: 100px;">Active: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "employee.active", actives_map, "class=\"form-control\""/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Create"/>
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
        if (this.value === "developer") {
            $('div.employeeTypeDisable').show();
        }
        else {
            $('div.employeeTypeDisable').hide();
        }
    });
    $('select.studioSelect').on('change', function() {
        let studio = this.value;
        $('#employeeType option').map(function () {
            if (this.getAttribute("value") === "director") {
                if (studio === "0") {
                    $(this).prop("selected", true);
                    console.log(this.getAttribute("value"));
                }
            }
            else {
                if (studio === "0") {
                    $(this).prop("selected", false);
                }
            }
        })
        if (studio === "0") {
            $('#employeeType').hide();
        }
        else {
            $('#employeeType').show();
        }
        $('#employeeType').change();

        let switched = false;
        $('#departmentID option').map(function () {
            if (this.getAttribute("data-studio") === studio) {
                $(this).show();
                $(this).prop("disable", false);
                if (!switched) {
                    $(this).prop('selected', true);
                    console.log(this.getAttribute("value"));
                    switched = true;
                }
                else {
                    $(this).prop('selected', false);
                }
            }
            else {
                $(this).hide();
                $(this).prop("disable", true);
                $(this).prop('selected', false);
            }
        })
        $('#departmentID').change();
    });
</script>