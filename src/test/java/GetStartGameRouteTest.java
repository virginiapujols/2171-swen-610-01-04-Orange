import com.webcheckers.appl.GameCenter;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetStartGameRoute;
import org.junit.Before;
import org.junit.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GetStartGameRouteTest {
    //Constants
    private static final String CHALLENGED = "challengedplayer";
    private static final String VIEW_NAME = "game.ftl";
    private static final String USERNAME = "username";
    private static final String USERNAME1 = "Andy";
    private static final String USERNAME2 = "Steve";

    //Attributes
    private Request request;
    private Session session;
    private Response response;
    private Session p1Session = mock(Session.class);
    private Session p2Session = mock(Session.class);
    private GameCenter gameCenter = new GameCenter();
    /**
     * The component under test(CuT)
     */
    private GetStartGameRoute CuT = new GetStartGameRoute(gameCenter);

    @Before
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        response = mock(Response.class);
    }

    @Test(expected = spark.HaltException.class)
    public void test_playerInGame() {
        gameCenter.addPlayer(p1Session, USERNAME1);
        gameCenter.addPlayer(p2Session, USERNAME2);
        gameCenter.startGame(USERNAME1, USERNAME2);

        when(p1Session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(p1Session);
        when(request.queryParams(CHALLENGED)).thenReturn(USERNAME2);

        CuT = new GetStartGameRoute(gameCenter);
        final ModelAndView result = CuT.handle(request, response);
    }

    @Test
    public void test_playerNotInGame() {
        gameCenter.addPlayer(p1Session, USERNAME1);
        gameCenter.addPlayer(p2Session, USERNAME2);

        when(p1Session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(p1Session);
        when(request.queryParams(CHALLENGED)).thenReturn(USERNAME2);

        CuT = new GetStartGameRoute(gameCenter);
        final ModelAndView result = CuT.handle(request, response);

        //Result is not null
        assertNotNull(result);

        //Model is non-null map
        final Object model = result.getModel();
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //Model contains all necessary View-Model data
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals(USERNAME1, vm.get(GetGameRoute.PLAYER_NAME));
        assertEquals(GetGameRoute.RED, vm.get(GetGameRoute.PLAYER_COLOR));
        assertEquals(Boolean.TRUE, vm.get(GetGameRoute.MY_TURN));
        assertEquals(USERNAME2, vm.get(GetGameRoute.OPP_NAME));
        assertEquals(GetGameRoute.WHITE, vm.get(GetGameRoute.OPP_COLOR));
        assertEquals(Boolean.TRUE, vm.get(GetGameRoute.CURR_PLAYER));

        //Test View Name
        assertEquals(VIEW_NAME, GetStartGameRoute.VIEW_NAME);
    }

    @Test(expected = spark.HaltException.class)
    public void test_noPlayers() {
        final ModelAndView result = CuT.handle(request, response);
    }
}
