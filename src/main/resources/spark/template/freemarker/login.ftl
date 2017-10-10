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
            <a href="/">Home</a>
        </div>

        <div class="body">

            <h3>Enter a Username to Log in</h3>
            <form id="signin" action="./login" method="POST">
                <input type="text" name="username" />
                <button type="submit">Login</button>
            </form>

            <#if message??>
                <div id="message" class="${messageType}">${message}</div>
            <#else>
                <div id="message" class="info" style="display:none">
                    <!-- keep here for Client-side messages -->
                </div>
            </#if>

        </div>

    </div>
</body>
</html>