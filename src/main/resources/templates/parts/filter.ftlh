<#macro filter url params vars any_filtres_already>
    <#assign elems = {}>
    <a class="btn btn-primary" data-toggle="collapse" href="#filterCollapse" role="button" aria-expanded="false" aria-controls="filterCollapse">
        <#if any_filtres_already!false>
            Filters*
        <#else>
            Filters
        </#if>
    </a>
    <br/>
    <style>
        .filterCollapse {
            border: 4px solid black;
            padding: 10px;
        }
    </style>
    <div  id="filterCollapse"
        <#if any_filtres_already>
            class="collapse filterCollapse show"
        <#else>
            class="collapse filterCollapse"
        </#if>
    >
        <form method="get" action="${url}" class="form-group">
            <#foreach param in params?keys>
                <span>${params[param][0]}</span>
                <#switch params[param][1]>
                    <#case 'input'>
                        <label>
                            <input type="text" class="form-control" name="${param}" value="${.vars[param]!}" placeholder="" />
                        </label>
                        <#break>
                    <#case 'boolean'>
                        <div class="btn-group btn-group-inline" >
                            <label>
                                <select name="${param}" class="form-control" >
                                    <#foreach idx in 2..4>
                                        <option value="${idx - 2}"
                                                <#if (idx - 2)?string == .vars[param]!>
                                                    selected
                                                </#if>
                                        >
                                            ${params[param][idx]}
                                        </option>
                                    </#foreach>
                                </select>
                            </label>
                        </div>
                        <#break>
                    <#case 'selection'>
                        <div class="btn-group btn-group-inline" >
                            <label>
                                <select name="${param}" class="form-control" >
                                    <#foreach idx in 2..(params[param]?sequence?size-1)>
                                        <option value="${params[param][idx][1]}"
                                                <#if params[param][idx][1]?string == .vars[param]!>
                                                    selected
                                                </#if>
                                        >
                                            ${params[param][idx][0]}
                                        </option>
                                    </#foreach>
                                </select>
                            </label>
                        </div>
                        <#break>
                    <#case 'date'>
                        <label>
                            <input type="text" class="form-control" id="datefilter" name=${param} value="${.vars[param]!}" placeholder="" />
                        </label>
                    <#default >
                </#switch>
                <br/>
            </#foreach>
            <br/>
            <button type="submit" class="btn btn-primary ml-2">Apply filters</button>
        </form>
        <#if any_filtres_already>
            <a type="button" class="btn btn-primary ml-2" href="${url}">Clear filters</a>
        </#if>
    </div>
</#macro>

<#function matches_any matched strs first_idx>
    <#foreach idx in first_idx..strs?size-1>
        <#if matched?string?lower_case == strs[idx]?string?lower_case>
            <#return true>
        </#if>
    </#foreach>
    <#return false>
</#function>