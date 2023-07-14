package importPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class importDestination {

	
	//@Test
	public void importtest()
	{
		System.out.println("-------");
		
		List<String> hrefs = new ArrayList<>();
		String json = new RestTemplate().getForObject("https://images-api.nasa.gov/search?q=clouds", String.class);
		new JsonParser().parse(json).getAsJsonObject()
		    .get("collection").getAsJsonObject()
		    .get("items").getAsJsonArray()
		    .forEach(item -> item.getAsJsonObject()
		        .get("links").getAsJsonArray()
		        .forEach(link -> {
		            JsonObject linkObject = link.getAsJsonObject();
		            String relString = linkObject.get("rel").getAsString();
		            if ("preview".equals(relString)) {
		                hrefs.add(linkObject.get("href").getAsString());
		            }
		        })
		    );
		System.out.println(hrefs);
		
	}
	

	List<String> payslis;
	List<String> payslisInsertIntoSQL;
	List<String> regnlis;
	List<String> regnlisInsertIntoSQL;
	
	@Test
	public void importwordcity() throws IOException
	{
		System.out.println("-------");
		String file =  "/tmp/worldcities-geo.csv";
		payslis = new ArrayList<>();
		payslisInsertIntoSQL = new ArrayList<>();
		
		regnlis = new ArrayList<>();
		regnlisInsertIntoSQL = new ArrayList<>();
		
		List<String> ls = new ArrayList<>();
		
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				loadidCity(strLine);
			}
			
			fstream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(payslis.size() + payslis.toString());
		System.out.println(payslisInsertIntoSQL.size() + payslis.toString());
		
		/*
		FileWriter myWriter = new FileWriter("/tmp/pasy.sql");
	    myWriter.write(payslisInsertIntoSQL.toString());
	    myWriter.close();*/
		
		FileWriter myWriter = new FileWriter("/tmp/regn.sql");
	    myWriter.write(regnlisInsertIntoSQL.toString());
	    myWriter.close();


		
	}
	
	public void loadidCity(String city)
	{
		
		String[] c = city.split(",");
		
		if(! payslis.contains(c[3]) &&  c[6].replace("\"", "").equals(""))
		{
			//payslis.add( insertPays (	c[2], c[3] , c[5], c[6]) );
			//payslisInsertIntoSQL.add( insertPaysInsertSQL (c[0], c[3] , c[4], c[5] ));
		}
		
		if(! regnlis.contains(c[6]) &&  !c[6].replace("\"", "").equals(""))
		{
			//		"2804038","520411","196","Senegal","SN","Africa","Diourbel","Amady Ounaré","15.34714","-13.01647"
			regnlis.add(c[6]);
			regnlisInsertIntoSQL.add( regnPaysInsertSQL (c[1], c[6] , new Long(c[0].replace("\"", "") ),   c[8] ,  c[9] ));
		}
	
			
	}
	
	/*
	cityid, regionid,countryid,countryname,countrycode,Continent,StateProvinceName,cityname,latitude,longitude

	"2955173","520112","1","Afghanistan","AF","Asia","Ghazni","‘Abd ul ‘Azīz Khān","30.9156","61.80703" */
	
	public String insertPays(String idpays ,  String paysnom,  String payscode, String continent)
	{
	
		return "INSERT INTO pays  (pays_id, libelle, code_pays, continent) VALUES(" +idpays   + ", '" + paysnom  + "', '" + payscode + "', '" + continent + "' " + ")";

			
	}
	
	public String insertPaysInsertSQL(String id , String pays , String codepays, String continent)
	{
		
		return " INSERT INTO pays  (pays_id, libelle, code_pays, continent) VALUES ("  + id.replace("\"", "")     + ", '" + pays.replace("\"", "")  + "' , '"+ codepays.replace("\"", "")    + "', '" + continent.replace("\"", "")  + "' " +   ");  \n";

			
	}
	
	
	public String regnPaysInsertSQL(String regnid , String regnLibelle , Long paysId,  String longitude, String latitude)
	{
		
		return "INSERT INTO regn_region  (regn_id, libelle, pays_id, longitude, latitude) "
				+ "VALUES (" + regnid.replace("\"", "")  +  " , '" +  regnLibelle.replace("\"", "") + "', " + paysId  + " ,'" + longitude.replace("\"", "") + "','" + latitude.replace("\"", "")  + "');\n";

			
	}
	
}
