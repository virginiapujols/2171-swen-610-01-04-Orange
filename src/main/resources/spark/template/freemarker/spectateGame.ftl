<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="4">
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
</head>
<body>
  <div class="page">
    <h1>Web Checkers</h1>
    
    <div class="navigation">
    <#if currentPlayer??>
      <a href="/">my home</a> |
      <a href="/logout">sign out [${playerName}]</a>
    <#else>
      <a href="/login">sign in</a>
    </#if>
    </div>
    
    <div class="body">
    <form id="gameForm" action="/submitTurn" method="POST">
      
      <p>
        You are spectating a game of checkers between ${player1Name} and ${player2Name}.
       <#if whoseTurn == 0>
        It's ${player1Name}'s turn.  The page will refresh periodically to show you updates.
       <#else>
        It's ${player2Name}'s turn.  The page will refresh periodically to show you updates.
       </#if>
      </p>
      
      <div>
        <div id="game-controls">
        
          <fieldset id="game-info">
            <legend>Info</legend>
            <div>
              <table data-color='RED' <#if whoseTurn == 0>class="isMyTurn"</#if>>
                <tr>
                  <td><img src="../img/single-piece-red.svg" /></td>
                  <td style="font-size: larger">${player1Name}</td>
                </tr>
              </table>
              <table data-color='WHITE' <#if whoseTurn == 1>class="isMyTurn"</#if>>
                <tr>
                  <td><img src="../img/single-piece-white.svg" /></td>
                  <td style="font-size: larger">${player2Name}</td>
                </tr>
              </table>
            </div>
          </fieldset>
          
          <fieldset id="game-toolbar">
            <legend>Controls</legend>
            <div>
              <a href="/endSpectating">Stop Spectating</a>
            </div>
          </fieldset>
          
        </div>
  
        <div class="game-board">
          <table id="game-board">
            <tbody>
            <#list board.iterator() as row>
              <tr data-row="${row.index}">
              <#list row.iterator() as space>
                <td data-cell="${space.cellIdx}"
                    <#if space.isValid() >
                    class="Space"
                    </#if>
                    >
                <#if space.piece??>
                  <div class="Piece"
                       id="piece-${row.index}-${space.cellIdx}"
                       data-type="${space.piece.type}"
                       data-color="${space.piece.color}">
                  </div>
                </#if>
                </td>
              </#list>
              </tr>
            </#list>
            </tbody>
          </table>
        </div>
      </div>

    </form>
    </div>
  </div>
</body>
</html>
