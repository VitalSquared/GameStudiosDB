<#import "../parts/common.ftlh" as c>
<#import "/spring.ftl" as spring />

<@c.page "Edit Contract">
    <br/>
    <form method="POST" action="/contracts/${contractID}/edit" enctype="multipart/form-data">
        <div class="form-group row offset-1" style="align-items: center;">
            <label for="date" style="width: 100px;">Date: </label>
            <div class="col-sm-5">
                <label for="date">
                    <@spring.bind "contract.date"/>
                    <input type="text" class="form-control" id="birthday" name="${spring.status.expression}" value="${(contract.date!.now)?string('YYYY-MM-dd')}" placeholder="" />
                    <@spring.showErrors "<br>" "color:red;"/>
                </label>
            </div>
        </div>

        <#assign values = {} />
        <#foreach value in 0..100>
            <#assign values = values + { value : value + "%" }>
        </#foreach>

        <div class="form-group row offset-1" style="align-items: center;">
            <label for="percent" style="width: 100px;">Percent: </label>
            <div class="col-sm-5">
                <@spring.formSingleSelect "contract.percent", values, "class=\"form-control\""/>
                <@spring.showErrors "<br>" "color:red;"/>
            </div>
        </div>

        <input type="submit" class="btn btn-primary offset-1" value="Update"/>
    </form>
</@c.page>