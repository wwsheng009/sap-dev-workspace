import java.io.InputStream;
import com.sapint.ws.RfcReadTableWS;
import com.sapint.wsclient.RfcReadTableWSService;
import com.sapint.wsclient.RfcWS;
import com.sapint.wsclient.RfcWSService;

public class TestClient2 {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		com.sapint.wsclient.RfcReadTableWS readtablews = new RfcReadTableWSService().getRfcReadTableWSPort();
//		readtablews.addCriteria(arg0);
		readtablews.addField("MANDT");
		String s = readtablews.getDataTable("MARA", 20,0);
//		System.out.println(s);
		
	}

}
