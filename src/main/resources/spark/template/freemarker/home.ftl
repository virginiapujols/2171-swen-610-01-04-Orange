<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <meta http-equiv="refresh" content="10">
        <title>${title} | Web Checkers</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
    </head>
    <body>
        <div class="page">

            <h1>Web Checkers</h1>

            <div class="navigation">
            <#if loggedPlayer??>
                <a href="/logout">Logout</a>
            <#else>
                <a href="/login">Login</a>
            </#if>
            </div>

            <div class="body">
                <p>Welcome to the world of online Checkers.</p>
            </div>


        </div>
    </body>
</html>