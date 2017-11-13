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
            <div id="greetingsMessage">
                <h3>Welcome to the world of online Checkers.</h3>
            </div>

            <#if username??>
                <div id="usernameList">
                    <#if usernames??>
                        <div class="subheading">
                            <p class="listHeader">Select a username to challenge them to a game!</p>
                            <hr />
                        </div>
                        <ul class="displayList">
                            <#list usernames as u>
                                <#if u != username>
                                    <li>
                                        <a href="/startGame?challengedplayer=${u}">${u}</a>
                                    </li>
                                </#if>
                            </#list>
                        </ul>
                    <#else>
                        <p>No other users are currently available for a game</p>
                    </#if>
                </div>

                <div id="gamesList">
                    <#if games??>
                        <div class="subheading">
                            <p class="listHeader">Select a game to spectate</p>
                            <hr />
                        </div>
                        <ul class="displayList">
                            <!--See http://freemarker.apache.org/docs/ref_builtins_string.html#ref_builtin_keep_before.
                            This built-in function does, in fact, accept a parameter and this error should not be generated. -->
                            <#list games as g>
                                <li>
                                    <a href="/spectateGame?player1=${g?keep_before(" vs.")}">${g}</a>
                                </li>
                            </#list>
                        </ul>
                    </#if>
                </div>

                <div id="leaderboard">
                    <div class="subheading">
                        <p class="listHeader">Online Leaderboard</p>
                        <hr />
                    </div>
                    <#if scores??>
                        <ul class="displayList">
                            <#list scores as item>
                                <li>${item}</li>
                            </#list>
                        </ul>
                    <#else>
                        <p>There is no score available yet.</p>
                    </#if>
                </div>
            </#if>
        </div>
    </div>
</body>
</html>