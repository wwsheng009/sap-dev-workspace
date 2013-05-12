import java.io.InputStream;
import java.io.StringBufferInputStream;

import com.sapint.wsclient.RfcWS;
import com.sapint.wsclient.RfcWSService;

public class TestClient1 {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RfcWS rfcws = new RfcWSService().getRfcWSPort();
		String functionName = "ZRFC*";
		String returnContent = rfcws.getRFCFunctionLIst(functionName);
		InputStream inputStream = new StringBufferInputStream(returnContent);
		
		System.out.println(returnContent);
	}

}
