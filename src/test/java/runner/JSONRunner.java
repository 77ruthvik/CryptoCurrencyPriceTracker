package runner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRunner {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        String path = System.getProperty("user.dir")+"//src//test//resources//testconfig.json";

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader(path));
        System.out.println(obj);

        JSONArray browsers = (JSONArray) obj.get("browsers");
        String parallel = (String) obj.get("parallel");
        JSONArray suites = (JSONArray) obj.get("CryptoSuites");

        TestNGRunner runner = new TestNGRunner(Integer.parseInt(parallel));

        for(int i=0; i<browsers.size(); i++) {
            //Iterating over the Browsers
            String browser_name = (String) browsers.get(i);
            runner.createSuite("CryptoManager"+browser_name, false);
            runner.addListener("listener.myTestNGListener");

            for(int j=0; j<suites.size(); j++) {
                JSONObject cur_data = (JSONObject) suites.get(j);
                String runmode = (String) cur_data.get("runmode");
                String name = (String) cur_data.get("name");
                String threshold = (String) cur_data.get("threshold");

                if(runmode.equals("Y")){
                    System.out.println(browser_name+"-"+runmode+"-"+name+"-"+threshold);
                    runner.addTest(browser_name+"-"+name+" test");
                    runner.addTestParameter("name", name);
                    runner.addTestParameter("threshold", threshold);
                    runner.addTestParameter("browser", browser_name);

                    List<String> incMethods = new ArrayList<String>();
                    incMethods.add("searchCrypto");
                    incMethods.add("getData");
                    runner.addTestClass("CryptoManager.TestFile", incMethods);

                }
            }
        }

        runner.run();
    }
    
}
