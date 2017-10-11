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

            <!-- Links for Login and Logout -->
            <div class="navigation">
            <#if username??>
                <a href="/logout">Logout</a>
            <#else>
                <a href="/login">Login</a>
            </#if>
            </div>

            <div class="body">
                <p>Welcome to the world of online Checkers.</p>

                <#if username??>
                    <p>Click a username to challenge them to a game!</p>
                    <ul>
                        <#list usernames as u>
                            <#if u != username>
                                <li>
                                    <a href="/startGame?challengedplayer=${u}">
                                    ${u}
                                    </a></li>
                            </#if>
                        </#list>
                    </ul>
                </#if>
            </div>


        </div>
    </body>
</html>