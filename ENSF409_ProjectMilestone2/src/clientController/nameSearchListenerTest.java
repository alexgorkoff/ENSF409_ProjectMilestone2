//package clientController;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import clientController.*;
//import junit.framework.Assert;
//import serverModel.SocketPack;
//
//public class nameSearchListenerTest {
//	SocketPack theSocket;
//	ClientController theClient;
//	ClientController.idSearchListener testCase;
//
//	@Before
//	public void setUp() throws Exception {
//		theClient = new ClientController(theSocket);
//		testCase = theClient.new idSearchListener();
//	}
//
//	@Test
//	public void test() {
//		Assert.assertEquals(17, testCase.actionPerformed());
//	}
