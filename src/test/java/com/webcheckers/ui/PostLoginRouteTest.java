package com.webcheckers.ui;
import com.webcheckers.appl.GameCenter;
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

    @Before
    public void beforeActions(){

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
        // Creating mock variables
        GameCenter gameCenter = mock(GameCenter.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Session session = mock(Session.class);
        String testName = "niharika";

        // Creating mock method calls
        when(request.session()).thenReturn(session);
        when(request.queryParams(USERNAME_PARAM)).thenReturn(testName);
        when(gameCenter.usernameTaken(testName)).thenReturn(true);

        //Executing the error function
        PostLoginRoute testPostLoginRoute = new PostLoginRoute(gameCenter);
        ModelAndView testModelAndView = testPostLoginRoute.handle(request, response);

        // Getting the result value from error function
        Map<String, Object> testVm = (HashMap)testModelAndView.getModel();
        String testViewName = testModelAndView.getViewName();

        // Comparing the expected and actual values
        assertEquals(VIEW_NAME, testViewName);
        assertEquals(USER_EXIST_MESSAGE, testVm.get(MESSAGE_ATTR));
    }

    @Test
    public void testHandle(){
        // Testing for error
        GameCenter gameCenter = mock(GameCenter.class);
        PostLoginRoute test = new PostLoginRoute(gameCenter);
        String reqUsername = "niharika";

        when(gameCenter.usernameTaken(reqUsername)).thenReturn(false);

        Session session = mock(Session.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);

        when(request.session()).thenReturn(session);
        when(request.queryParams(USERNAME_PARAM)).thenReturn(reqUsername);

        ModelAndView testModelAndView = test.handle(request, response);
        String viewName = testModelAndView.getViewName();
        Map<String, Object> testVm = (HashMap)testModelAndView.getModel();

        assertEquals(reqUsername, testVm.get(USERNAME_PARAM));
        assertEquals(GetHomeRoute.VIEW_NAME, viewName);
    }
}