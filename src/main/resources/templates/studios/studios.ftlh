<#import "../parts/common.ftlh" as c>
<#import "_header.ftlh" as header>
<#import "../parts/security.ftlh" as s>

<@c.page "${myStudio.getName()}">
    <@header.header/>
    <br/>

    <#if s.isAdmin>
        <a class="btn btn-primary" href="/studios/new">Create new studio</a>
    </#if>

    <div class="accordion" id="studiosAccordion">
        <#foreach studio in studios>
            <div class="card">
                <div class="card-header" id="headingOne">
                    <h5 class="mb-0">
                        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapse${studio.studioID}" aria-expanded="true" aria-controls="collapse${studio.studioID}">
                            ${studio.getName()}
                        </button>
                    </h5>
                </div>

                <div id="collapse${studio.studioID}" aria-labelledby="heading${studio.studioID}" data-parent="#studiosAccordion"
                    <#if studio.studioID == myStudio.studioID>
                        class="collapse show"
                    <#else>
                        class="collapse"
                    </#if>
                >
                    <div>
                        Address: ${studio.getAddress()}
                        <#if s.isAdmin>
                            <a class="btn btn-primary" href="/studios/${studio.getStudioID()}/edit">Edit</a>
                        </#if>
                    </div>
                </div>
            </div>
        </#foreach>
</@c.page>