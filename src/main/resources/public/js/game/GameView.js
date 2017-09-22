/**
 * This module exports the GameView class constructor.
 * 
 * This component manages the Client-side behavior of the Game view.
 * It uses the State pattern to handle the changes in UI behavior
 * as the view changes from one state to another.
 */
define(function(require){
  'use strict';
  
  // imports
  var GameConstants = require('GameConstants');
  var StartState = require('StartState');
  var EmptyTurnState = require('EmptyTurnState');
  var PendingMoveState = require('PendingMoveState');
  var StableTurnState = require('StableTurnState');
  var WaitForTurnState = require('WaitForTurnState');
  var CheckMyTurnState = require('CheckMyTurnState');

  //
  // Constants
  //

  var BACKUP_LINK = 'backupLink';
  var RESET_LINK = 'resetLink';
  var SUBMIT_LINK = 'submitLink';
  
  var HOVER_CLASS = 'hover';
  var PENDING_CLASS = 'pending';
  var VALID_CLASS = 'valid';
  var PIECE_CLASS = 'Piece';
  var SPACE_CLASS = 'Space';
  
  var PIECE_ID = 'pieceId';
  
  // a map for the opposing color
  var OPPOSITION_MAP = { RED: 'WHITE' , WHITE: 'RED' };

  /**
   * Constructor function.
   */
  function GameView(options) {
    var state = null;
    var pendingMove = null;
    var turn = [];
    var pieces = [];
    var $activePiece = null;
    
    // create states and a lookup map
    var stateMap = {};
    stateMap[GameConstants.START_STATE] = new StartState(this);
    stateMap[GameConstants.EMPTY_TURN] = new EmptyTurnState(this);
    stateMap[GameConstants.PENDING_MOVE] = new PendingMoveState(this);
    stateMap[GameConstants.STABLE_TURN] = new StableTurnState(this);
    stateMap[GameConstants.WAIT_FOR_MY_TURN] = new WaitForTurnState(this);
    stateMap[GameConstants.CHECK_MY_TURN] = new CheckMyTurnState(this);
    
    // Domain methods

    this.getPlayer = function getPlayer() {
      return options.player;
    };

    this.getPlayerColor = function getPlayerColor() {
      return options.player.color;
    };

    this.getOpponent = function getOpponent() {
      return options.opponent;
    };
    
    this.getOpponentColor = function getOpponentColor() {
      return OPPOSITION_MAP[this.getPlayerColor()];
    }

    this.getOpponentName = function getOpponentName() {
      return options.opponent.name;
    };
    
    this.isMyTurn = function isMyTurn() {
      return options.player.isMyTurn;
    };
    
    this.isPlayerRed = function isPlayerRed() {
      return options.player.color === 'RED';
    };
    
    this.getPendingMove = function getPendingMove() {
      return pendingMove;
    };
    
    /**
     * Sets the move that was just requested by the player.
     * It is 'pending' until the server validates it.
     */
    this.setPendingMove = function setPendingMove(_pendingMove) {
      if (_pendingMove === undefined || _pendingMove === null) {
        throw new Error("_pendingMove must not be null");
      }
      //
      pendingMove = _pendingMove;
      // move the Piece
      var $piece = getPiece$(_pendingMove.start);
      if ($piece === null) throw new Error("No Piece found at: " + _pendingMove.start);
      // start the 'pending' style animations
      $piece.parent().addClass(PENDING_CLASS);
      getSpace$(_pendingMove.end).addClass(PENDING_CLASS);
      // move the Piece on the board
      movePiece($piece, _pendingMove);
    };
    
    /**
     * Remove pending move from consideration.
     * Thus putting the Piece back to the starting point.
     */
    this.resetPendingMove = function resetPendingMove() {
      // clear the 'pending' styles
      getSpace$(pendingMove.start).removeClass(PENDING_CLASS);
      getSpace$(pendingMove.end).removeClass(PENDING_CLASS);
      // move the Piece back
      undoMove(pendingMove);
      // clear the state variable
      pendingMove = null;
    };
    
    /**
     * Add the server-validated pending move to the turn.
     */
    this.addPendingMove = function addPendingMove() {
      // is this the first move?  if so then store the $activePiece
      if ($activePiece === null) {
        $activePiece = getPiece$(pendingMove.end);
        console.info('$activePiece', $activePiece);
      }
      // change the 'pending' to 'valid'
      getSpace$(pendingMove.start).removeClass(PENDING_CLASS).addClass(VALID_CLASS);
      getSpace$(pendingMove.end).removeClass(PENDING_CLASS).addClass(VALID_CLASS);
      // store the move on the turn list
      turn.push(pendingMove);
      // clear the state variable
      pendingMove = null;
    };

    /**
     * Remove the most recent move from the turn.
     * 
     * @return
     *    true if there are more moves remaining; otherwise, false
     */
    this.popMove = function popMove() {
      if (turn.length === 0) return false;
      //
      var move = turn.pop();
      undoMove(move);
      // clear 'valid' styles
      getSpace$(move.end).removeClass(VALID_CLASS);
      var isNowEmpty = turn.length === 0;
      if (isNowEmpty) {
        getSpace$(move.start).removeClass(VALID_CLASS);
      }
      //
      return isNowEmpty;
    };

    this.isTurnActive = function isTurnActive() {
      return turn.length > 0;
    };

    this.getTurn = function getTurn() {
      return turn;
    };

    // UI Controls

    this.enableAllMyPieces = function enableAllMyPieces() {
      jQuery.each(pieces, function(idx) {
        enablePiece(this);
      });
    };

    this.disableAllMyPieces = function disableAllMyPieces() {
      jQuery.each(pieces, function(idx) {
        disablePiece(this);
      });
    };

    this.enableActivePiece = function enableActivePiece() {
      // pre-conditions
      if ($activePiece === null) {
        throw new Error("No active Piece.");
      }
      //
      enablePiece($activePiece);
    };

    // Stateful API

    this.getState = function getState() {
      return state;
    };
    
    this.setState = function setState(stateName) {
      // find the state and check if this is a change
      var newState = findState(stateName);
      if (state === newState) return;
      // exit the current state (if known)
      // enter the next state
      setTimeout(function() {
        console.info('Entering state ' + newState.getName());
        newState.onEntry();
        state = newState;
      }, 0); // delay entry until after the DOM updates
    };

    /**
     * Request a move; could be a single move or a jump.
     * This message has state-specific behavior.
     */
    this.requestMove = function requestMove(pendingMove) {
      delegateStateMessage('requestMove', arguments);
    }

    /**
     * Reset the whole turn.  This message has state-specific behavior.
     */
    this.resetTurn = function resetTurn() {
      delegateStateMessage('resetTurn', arguments);
    }

    /**
     * Backup a single move.  This message has state-specific behavior.
     */
    this.backupMove = function backupMove() {
      delegateStateMessage('backupMove', arguments);
    }

    /**
     * This user action submits a turn to the server.
     */
    this.submitTurn = function submitTurn() {
      delegateStateMessage('submitTurn', arguments);
    }

    /**
     * Resign from the game.
     * 
     * This action leaves the current Game view and retrieves an
     * updated Game view from the server.
     */
    this.resignGame = function resignGame() {
      // TODO
      alert('NYI: Resign action');
    }

    // Private (internal) functions

    function delegateStateMessage(actionSymbol, args) {
      if (state[actionSymbol] !== undefined) {
        state[actionSymbol].apply(state, args);
      } else {
        throw new Error("The '" + state.getName() + "' doesn't handle a '" + actionSymbol + "' message.");
      }
    }
    
    function findState(stateName) {
      if (stateName === null || stateName === undefined) {
        throw new Error("no stateName");
      }
      // find state
      var newState = stateMap[stateName];
      if (newState === null || newState === undefined) {
        throw new Error("Unknown state: " + stateName);
      }
      //
      return newState;
    }

    function undoMove(move) {
      var $piece = getPiece$(move.end);
      if ($piece === null) {
        throw new Error("No Piece found at: " + move.end);
      }
      movePiece($piece, reverseMove(move));
    }

    // initialize the view's controls
    initializeTurnControls(this);

    // create a list of my Piece elements
    jQuery(makePieceSelector(this.getPlayerColor())).each(function(idx) {
      // record each Piece element
      pieces.push(this);
    });

    // initialize D-n-D listeners for Space elements
    initializeDragHandlers(this);
  }

  //
  // Public (external) methods
  //
  
  GameView.prototype.disableBackupAction = function disableBackupAction() {
    disableLink(BACKUP_LINK);
  };

  GameView.prototype.enableBackupAction = function enableBackupAction() {
    enableLink(BACKUP_LINK);
  };

  GameView.prototype.disableResetAction = function disableResetAction() {
    disableLink(RESET_LINK);
  };

  GameView.prototype.enableResetAction = function enableResetAction() {
    enableLink(RESET_LINK);
  };

  GameView.prototype.disableSubmitAction = function disableSubmitAction() {
    disableLink(SUBMIT_LINK);
  };

  GameView.prototype.enableSubmitAction = function enableSubmitAction() {
    enableLink(SUBMIT_LINK);
  };

  GameView.prototype.enableAllControls = function enableAllControls() {
    jQuery("div#game-controls a").removeAttr('disabled');
  };

  GameView.prototype.disableAllControls = function disableAllControls() {
    jQuery("div#game-controls a").attr('disabled', 'disabled');
  };

  GameView.prototype.displayMessage = function displayMessage(message) {
    jQuery('#message').attr('class', message.type).html(message.text).slideDown(400);
  };

  /**
   * Beep to alert the player that it's their turn
   */
  GameView.prototype.beep = function beep() {
    var sound = document.getElementById("audio");
    sound.play();
  }
  
  //
  // Private (external) functions
  //

  /**
   * Create a jQuery selector to select all Piece DIV elements for a certain game color.
   */
  function makePieceSelector(color) {
    return '#game-board div.' + PIECE_CLASS + '[data-color=' + color + ']';
  }

  function disableLink(linkID) {
    jQuery("a#" + linkID).removeAttr('disabled');
  }

  function enableLink(linkID) {
    jQuery("a#" + linkID).attr('disabled', 'disabled');
  }

  /**
   * Enable a given piece to be draggable.
   */
  function enablePiece(piece) {
    jQuery(piece)
    // enable draggable
    .attr('draggable', 'true')
    // add the visual cue that the Piece can move (ie, draggable)
    .css('cursor', 'move')
    // attach these handlers
    .on({
      "dragstart" : handleDragStart
    });
  }

  /**
   * Disable a given piece from being draggable.
   */
  function disablePiece(piece) {
    jQuery(piece)
    // disable draggable
    .attr('draggable', 'false')
    // remove visual cue
    .css('cursor', 'inherit')
    // remove handlers
    .off('dragstart')
    .off('dragend');
  }

  /**
   * Move a Piece DOM element from one space to another.
   */
  function movePiece($piece, move) {
    var $fromCell = $piece.parent();
    var $toCell = getSpace$(move.end);
    // hide the DnD visual cue on destination (move 'to') cell
    $toCell.removeClass(HOVER_CLASS);
    // move the piece to the new location
    $piece.appendTo($toCell);
    // change the status of the source and destination cells
    $fromCell.addClass(SPACE_CLASS);
    $toCell.removeClass(SPACE_CLASS);
  }

  /**
   * Gets a jQuery element for a specific position.
   */
  function getSpace$(position) {
    var selector =
      'tr[data-row=' + position.row
      + '] td[data-cell=' + position.cell + ']';
    return jQuery(selector);
  }

  /**
   * Gets a jQuery element for the Piece as a specific position.
   * Returns null if there is no Piece at that Space.
   */
  function getPiece$(position) {
    var $space = getSpace$(position);
    var $piece = $space.find('div.Piece');
    return $piece.length === 0 ? null : $piece;
  }
  
  /**
   * Create a Move JSON representation from two Space DOM elements.
   */
  function makeMove($startEl, $endEl) {
    var startRow = $startEl.parent().attr('data-row');
    var startCell = $startEl.attr('data-cell');
    var endRow = $endEl.parent().attr('data-row');
    var endCell = $endEl.attr('data-cell');
    return {
      start: {row: startRow, cell: startCell},
      end: {row: endRow, cell: endCell}
    };
  }

  /**
   * Create a Move object that is the reverse of the parameter.
   */
  function reverseMove(move) {
    return { start: move.end, end : move.start };
  }

  //
  // Action handlers
  //

  function initializeTurnControls(view) {
    console.debug('initializeTurnControls');
    
    function backupMove(view, event) {
      view.backupMove();
    }

    // initialize view controls
    jQuery('#' + BACKUP_LINK).on('click',
            makeViewEventHandler(view, backupMove));
    jQuery('#' + RESET_LINK).on('click',
            makeViewEventHandler(view,
                    function(view, event) { view.resetTurn(); }));
    jQuery('#' + SUBMIT_LINK).on('click',
            makeViewEventHandler(view,
                    function(view, event) { view.submitTurn(); }));
    jQuery('#resignLink').on('click',
            makeViewEventHandler(view,
                    function(view, event) { view.resignGame(); }));

    // TODO: initialize view keystrokes
    // jQuery(document).on('keypress', makeKeyHandler(backupHandler, 'u'));
    // jQuery(document).on('keypress', makeKeyHandler(resetHandler, 'r'));
    // jQuery(document).on('keypress', makeKeyHandler(submitTurnHandler, 's'));
    // jQuery(document).on('keypress', makeKeyHandler(resignHandler, 'R'));

  }
  
  function makeViewEventHandler(view, action) {
    return function(event) {
      console.info('event:', event);
      // short-circuit if disabled
      if ($(this).is("[disabled]")) {
        event.preventDefault();
        return;
      }
      // invoke the action
      action(view, event);
    }
  }

  /**
   * This user action resets the board back to the starting point.
   */
  function resetHandler(event) {
    // short-circuit if disabled
    if ($(this).is("[disabled]")) {
      event.preventDefault();
      return;
    }
    // clear the turn
    resetBoard();
  }

  //
  // Piece drag-and-drop handlers
  //

  function initializeDragHandlers(view) {
    // force no drag support for the opponent pieces
    jQuery(makePieceSelector(view.getOpponentColor()))
    // disable dragging behavior
    .on({ "dragstart" : false });

    jQuery('#game-board').on({
      "dragenter": handleDragEnter,
      "dragover": handleDragOver,
      "dragleave": handleDragLeave,
      "drop": function(event) {
        event.preventDefault();
        handleDrop(view, event);
      }
    },
    // attach these handlers dynamically to all Space elements
    "td." + SPACE_CLASS);
  }

  function handleDragStart(event) {
    var piece = jQuery(event.target);
    piece.css('opacity' , '0.75');
    event.originalEvent.dataTransfer.effectAllowed = 'move';
    event.originalEvent.dataTransfer.setData('text', piece.attr('id'));
    return true;
  }

  function handleDragEnd(event) {
    jQuery(event.target).css('opacity' , '1');
  }

  function handleDragOver(event) {
    event.preventDefault();
    event.originalEvent.dataTransfer.dropEffect = 'move';
    return false;
  }

  function handleDragEnter(event) {
    jQuery(event.target).addClass(HOVER_CLASS);
  }

  function handleDragLeave(event) {
    jQuery(event.target).removeClass(HOVER_CLASS);
  }

  function handleDrop(view, event) {
    var $destCell = jQuery(event.target);
    var pieceId = event.originalEvent.dataTransfer.getData('text');
    var $piece = jQuery('#' + pieceId);
    var $sourceCell = $piece.parent();
    // set to normal opacity
    $piece.css('opacity' , '1');
    // store the pending move
    var pendingMove = makeMove($sourceCell, $destCell);
    view.requestMove(pendingMove);
    //
    return false;
  }

  
  
  // export class constructor
  return GameView;
  
});

