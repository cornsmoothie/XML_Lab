package xmllab;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;



public class XMLlab {

    private final String USER_AGENT = "Mozilla/5.0";
    
    public static void main(String[] args) {
        
        
		XMLlab http = new XMLlab();

		System.out.println("Testing 1 - Send Http GET request");
        try {
            http.sendGet();
        } catch (Exception ex) {
            Logger.getLogger(XMLlab.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void sendGet() throws Exception {


                Scanner input = new Scanner(System.in);
                
                System.out.println("Put ID");
                int id = input.nextInt();
        

		String url = "http://129.32.92.49/xml/grade.php?id=" + id;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

                /*
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
                */
		//in.close();
                
        DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(con.getInputStream());
         
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" 
            + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("message");
         System.out.println("----------------------------");
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" 
               + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("Name:" + eElement.getElementsByTagName("name")
                       .item(0)
                       .getTextContent());
               System.out.println("Grade: " 
                  + eElement
                  .getElementsByTagName("grade")
                  .item(0)
                  .getTextContent());

            }
         }


                
               

                in.close();
		//print result
		//System.out.println(response.toString());
                        

	}
    

    
}
