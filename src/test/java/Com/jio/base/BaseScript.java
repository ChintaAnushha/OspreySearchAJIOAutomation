package Com.jio.base;

import Com.jio.model.survey.global.TestData;
import Com.jio.model.survey.global.TestDataListYMLWrapper;
import org.testng.annotations.BeforeClass;
import org.yaml.snakeyaml.Yaml;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Fazil created on 06-Dec-2022
 */
public class BaseScript {

    protected Properties prop;
    protected FileInputStream fis;
    static Map<String, String> map = new HashMap<String, String>();
    public static HashMap<String,String> headerMap = new HashMap<String,String>();
    public String propertyVar = "";
    public Map<String, String> getEnv;
    public static String fqdn = "";
    public static String cookie = "";
    public static String auth_token = "";
    public static String token_type = "";
    public static int expStatusCode = 200;

    static Connection con = null;
    // Statement object
    public static Statement stmt;

    public String DB_URL;
    public String DB_USER;
    public String DB_PASSWORD;




    @BeforeClass
    public void launchApp() throws FileNotFoundException {
        try {

            //propertyVar = System.getProperty("URL");
            propertyVar = "SIT";
            getEnv = Com.jio.base.Config.getProp(propertyVar);
            fqdn = getEnv.get("URL");
            org.apache.log4j.PropertyConfigurator
                    .configure(System.getProperty("user.dir") + "/src/test/java/log4j.properties");
            prop = new Properties();
            fis = new FileInputStream(System.getProperty("user.dir") + "/testdata/testdata.properties");
            prop.load(fis);
            cookie=prop.getProperty("api-key");
            map.put("baseUrl", prop.getProperty("baseUrl"));
            map.put("dbUrl", prop.getProperty("dbUrl"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public TestData getYMLData(String path, String testCaseName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TestDataListYMLWrapper testData = yaml.loadAs(inputStream, TestDataListYMLWrapper.class);
        return testData.getTestDataMap().get(testCaseName);
    }

//        @BeforeSuite
//        public void setUp() throws Exception {
//            try{
//                // Make the database connection
//                String dbClass = "com.mysql.jdbc.Driver";
//                Class.forName(dbClass).newInstance();
//                // Get connection to DB
//                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//                // Statement object to send the SQL statement to the Database
//                stmt = con.createStatement();
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//    public void test() {
//        try{
//            String query = "select * from userinfo";
//            // Get the contents of userinfo table from DB
//            ResultSet res = stmt.executeQuery(query);
//            // Print the result untill all the records are printed
//            // res.next() returns true if there is any next record else returns false
//            while (res.next())
//            {
//                System.out.print(res.getString(1));
//                System.out.print("\t" + res.getString(2));
//                System.out.print("\t" + res.getString(3));
//                System.out.println("\t" + res.getString(4));
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }



}
