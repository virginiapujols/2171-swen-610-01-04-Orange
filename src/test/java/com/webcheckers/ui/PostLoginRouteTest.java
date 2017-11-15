package com.webcheckers.ui;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import java.util.HashMap;
import java.util.Map;
import static com.webcheckers.ui.PostLoginRoute.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class PostLoginRouteTest {
    // Atributes
    private GameCenter gameCenter = mock(GameCenter.class);
    private Game game = mock(Game.class);
    private String _username = "Niharika";
    private Request request;
    private Response response;
    private Session session;

    /**
     * The component under test(CuT)
     */
    private PostLoginRoute CuT = new PostLoginRoute(gameCenter);

    @Before
    public void setUp(){
        game = mock(Game.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
    }

    @Test
    public void testPostLoginRoute(){
        // Checking if we send a null gameCenter, it gives an appropriate message
        try {
            PostLoginRoute test = new PostLoginRoute(null);
        }
        catch (NullPointerException e){
            assertEquals("gameCenter must not be null", e.getMessage());
        }

        // Checking if the gameCenter is stored properly in the class
        GameCenter mockGameCenter = mock(GameCenter.class);
        PostLoginRoute test = new PostLoginRoute(mockGameCenter);
        assertEquals(mockGameCenter, test.getGameCenter());
    }

    @Test
    public void testError(){
        // Creating mock method calls
        when(request.session()).thenReturn(session);
        when(request.queryParams(USERNAME_PARAM)).thenReturn(_username);
        when(gameCenter.isUsernameTaken(_username)).thenReturn(true);

        //Executing the error function
        PostLoginRoute CuT = new PostLoginRoute(gameCenter);
        ModelAndView result = CuT.handle(request, response);

        // Getting the result value from error function
        Map<String, Object> testVm = (HashMap)result.getModel();
        String testViewName = result.getViewName();

        // Comparing the expected and actual values
        assertEquals(VIEW_NAME, testViewName);
        assertEquals(USER_EXIST_MESSAGE, testVm.get(MESSAGE_ATTR));
    }

    @Test (expected = spark.HaltException.class)
    public void testHandle(){
        // Testing for error
        when(gameCenter.isUsernameTaken(_username)).thenReturn(false);

        when(request.session()).thenReturn(session);
        when(request.queryParams(USERNAME_PARAM)).thenReturn(_username);

        ModelAndView testModelAndView = CuT.handle(request, response);
        String viewName = testModelAndView.getViewName();
        Map<String, Object> testVm = (HashMap)testModelAndView.getModel();

        assertEquals(_username, testVm.get(USERNAME_PARAM));
        assertEquals(GetHomeRoute.VIEW_NAME, viewName);
    }
}