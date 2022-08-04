<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <@l.logout />
        <span><a href="/user">User list</a></span>
    </div>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="name" placeholder="Name">
            <input type="text" name="description" placeholder="Description">
            <input type="number" name="price" placeholder="Price">
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Add!</button>
        </form>
    </div>
    <div>Items for rent</div>
    <form action="/main" method="get">
        <input type="text" name="filter" value="${filter?ifExists}"/>
        <button type="submit">Search</button>
    </form>
    <#list items as item>
        <div>
            <span>${item.name}</span>
            <span>${item.description}</span>
            <span>${item.price}</span>
            <strong>${item.ownerName}</strong>
            <div>
                <#if item.filename??>
                    <img src="/img/${item.filename}">
                </#if>
            </div>
        </div>
    <#else>
        No items
    </#list>
</@c.page>