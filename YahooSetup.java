package Validations;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class YahooSetup {

//  static boolean flag = false;

  //HELPER FUNCTIONS

  /**
   * Params - String, ArrayList
   * Description - Parses the url and extracts parameters from it
   * Output - Map of URL parameters which are seperated by &
   **/

  public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
    Map<K, V> map
  ) {
    List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Entry.comparingByValue());

    Map<K, V> result = new LinkedHashMap<>();
    for (Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

  public static void sendSmtpMail(
	
    ArrayList<String> flagList,
    String xlsxOUTPUT,
    String cSV_TO_PROCESS_FINAL, Map<String, Integer> sortedVal, String dateInString
  ) {
    String to = "janice.n@affinity.com";

    // Sender's email ID needs to be mentioned
    String from = "donotreply@affinity.com";

    // Assuming you are sending email through relay.jangosmtp.net
//    String host = "localhost";

    Properties props = new Properties();
//
//    props.put("mail.smtp.host", "smtp.office365.com");
//    props.put("mail.smtp.port", "587");
//    props.put("mail.smtp.auth", "true");
//    props.put( "mail.smtp.starttls.enable", "true");
//    props.put("mail.smtp.tls.enable","true");
//    props.put("mail.smtp.socketFactory.fallback","true");
//    
    
    
    
    
    
    props.put("mail.smtp.from", from);
    props.put("mail.smtp.host", "178.62.242.109");
    props.put("mail.smtp.port", "2526");
   
//    props.put("mail.smtp.socketFactory.port", port);
//    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//    props.put("mail.smtp.socketFactory.fallback", "false");

    //create Authenticator object to pass in Session.getInstance argument

	
	

	
    String ogFile = cSV_TO_PROCESS_FINAL.substring(45,56);
    // Get the Session object.
    
    System.out.print(ogFile);
     
    String y = ogFile.substring(0,4);
    String m = ogFile.substring(4,6);
    String d = ogFile.substring(6,8);
   
   
    Session session = Session.getDefaultInstance(props);

    try {
      // Create a default MimeMessage object.
      Message message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));

      // Set To: header field of the header.
      message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse(to)
      );
      
      message.addRecipient(RecipientType.CC, new InternetAddress(
              "qa.siteplug@affinity.com"));
   //   message.addRecipient(RecipientType.CC, new InternetAddress(
     //        "melissa.g@affinity.com"));
     // message.addRecipient(RecipientType.CC, new InternetAddress(
       //       "jahnvi.kumar@affinity.com"));
      
      //message.setRecipient(Message.RecipientType.CC, "abc@abc.example,abc@def.example,ghi@abc.example");

      // Set Subject: header field
      message.setSubject("[INFO] YAHOO FIXED SETUP ANALYSIS REPORT");

      
      
      // Create the message part
      BodyPart messageBodyPart = new MimeBodyPart();

      // Now set the actual message

      String severity = "";
      // Create a multipart message
      String htmlStr =
        " <h1>Issues in the yahoo fixed setup data for [ " + dateInString + " ] is listed below, Report is attached in mail for your reference </h1><table style=\"width:100%;border: 1px solid black;  border-collapse: collapse; font-weight:bold;\">\n" +
        "        <tr>\n" +
        "            <th style = \"padding: 10px;border: 1px solid black;  border-collapse: collapse; background-color:black; color:white;\">Issue</th>\n" +
        "            <th style =  \"padding: 10px;border: 1px solid black;  border-collapse: collapse; background-color:black; color:white; \">Number of cases</th>\n" +
        "            <th style =  \"padding: 10px;border: 1px solid black;  border-collapse: collapse; background-color:black; color:white;\">Severity</th>\n" +
        "        </tr>\n";

      for (String i : flagList) {
        if (
          i.equals("Subid is missing!") ||
      
          i.equals("Deeplink is not encoded")
        ) {
          severity =
            "<td style= \"padding: 10px;  border: 1px solid black;  border-collapse: collapse; color:white; background-color:red;\">HIGH</td>";
        }

        if (i.equals("Country code is invalid") || i.equals("Click ID is not unique!") || i.equals("Kingdomain not found in deeplink")) {
          severity =
            "<td style= \"padding: 10px;  border: 1px solid black;  border-collapse: collapse; color:black; background-color:yellow;\">MEDIUM</td>";
        }
        htmlStr +=
          "<tr><td style= \"padding: 10px;  border: 1px solid black;  border-collapse: collapse;\">" +
          i +
          "</td><td style=\"padding: 20px;  border: 1px solid black;  border-collapse: collapse;\">" +
          sortedVal.get(i) +
          "</td>" +
          severity +
          "</tr>";
      }
      htmlStr += "</table><br><br> <h2>--QA SITEPLUG</h2>";
      Multipart multipart = new MimeMultipart();
      BodyPart attachmentBodyPart = new MimeBodyPart(); //2

      DataSource source = new FileDataSource(xlsxOUTPUT);
      attachmentBodyPart.setDataHandler(new DataHandler(source)); //2
      attachmentBodyPart.setFileName(xlsxOUTPUT.substring(27)); // 2
      multipart.addBodyPart(attachmentBodyPart); //3
      BodyPart htmlBodyPart = new MimeBodyPart(); //4
      htmlBodyPart.setContent(htmlStr, "text/html"); //5
      multipart.addBodyPart(htmlBodyPart); // 6
      message.setContent(multipart); //7
      Transport.send(message);
      System.out.println("Sent message successfully....");
      
      
      
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public static void csvToXLSX(String csvSrc, String xlsxDestination) {
    try {
      XSSFWorkbook workBook = new XSSFWorkbook();
      XSSFSheet sheet = workBook.createSheet("sheet1");
      String currentLine = null;
      int RowNum = 0;
      BufferedReader br = new BufferedReader(new FileReader(csvSrc));

      while ((currentLine = br.readLine()) != null) {
        String str[] = currentLine.split("\\|");
        RowNum++;
        XSSFRow currentRow = sheet.createRow(RowNum);
        for (int i = 0; i < str.length; i++) {
          currentRow.createCell(i).setCellValue(str[i]);
        }
      }

      FileOutputStream fileOutputStream = new FileOutputStream(xlsxDestination);
      workBook.write(fileOutputStream);
      fileOutputStream.close();
    } catch (Exception ex) {
      System.out.println(ex.getMessage() + "Exception in try");
    }
  }

  public static Map<String, String> getQueryMap(
    String query,
    ArrayList<String> duplicates
  ) {
    String[] params = query.split("&");
    Map<String, String> map = new HashMap<String, String>();

    for (String param : params) {
      if (param.split("=").length > 1) {
        String name = param.split("=")[0];

        String value = param.split("=")[1];
        if (!map.containsKey(name)) {
          map.put(name, value);

          /** DUPLICATE PARAMETER LOGIC **/
          duplicates.remove(name.toLowerCase());
        } else {
          duplicates.add(name.toLowerCase());
        }
      }
    }

    return map;
  }

  /**
   * Param - String
   * Description - Checks weather a url is encoded or not
   * Output - Boolean value based on the url's encoding
   * **/

  public static boolean checkEncode(String passedUrl) {
    boolean isEncoded = false;

    String EncodedURL = URLEncoder.encode(passedUrl, StandardCharsets.UTF_8);

    if (!passedUrl.equals(EncodedURL)) {
      if (passedUrl.matches(".*[\\ \\% \"\\<\\>\\{\\}|\\\\^~\\[\\]].*")) {
        isEncoded = true;
      }
    } else {
      return isEncoded;
    }
    return isEncoded;
  }
  
  public static void main(String[] args)
    throws InterruptedException, IOException {
    /** TIME/CALENDER VARIABLES **/
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    long curr = timestamp.getTime();
    String pattern = "dd-MM-yyyy";
    String dateInString = new SimpleDateFormat(pattern).format(new Date());
    long startTime = System.currentTimeMillis();

    Date date = new Date(curr);
    Timestamp ts = new Timestamp(date.getTime());

    /** FILE VARIABLES **/
    String CSV_TO_PROCESS_FINAL =
     "C:\\Users\\Arunkumar\\Desktop\\sdc_fixed_urls_qa_20230115.csv";
   final String ALLOWED_GEO_LIST = "C:\\Users\\Arunkumar\\Desktop\\Countrycode.csv";

   final String OUTPUT_FILE_NAME =
     "C:\\Users\\Arunkumar\\Desktop\\Fixed_Setup_Validation_" +
     dateInString +
     "_" +
     curr +
     ".csv";
   
//   final String OUTPUT_DESCRIPTION = "/home/vishal/Desktop/DESC_Fixed_Setup_Validation_" +
//   	      dateInString +
//   	      "_" +
//   	      curr +
//   	      ".csv";	

    final File file = new File(OUTPUT_FILE_NAME);

    boolean subIdMissingFlag = false;
    boolean clickIdNotUniqueFlag = false;
    boolean countryCodeInvalidFlag = false;
    boolean deepLinkNotEncodedFlag = false;
    boolean kingDomainNotInDlFlag = false;
    boolean invalidCaseFlag = false;
    boolean repeatParamsFlag = false;
    
    int subIdNullCtr = 0;
    int clickIdNonUniqueCtr = 0;
    int countryCodeInvalidCtr = 0;
    int parameterMismatchCtr = 0;
    int repeatParamsCtr = 0;
    int kingDomainNotFoundCtr = 0;
    int deepLinkNotEncodedCtr = 0;
    
    /** MAP AND LIST VARIABLES **/

    Map<String, String> caseParameterCheck = new HashMap<String, String>();
    ArrayList<String> checkDuplicateParams = new ArrayList<String>();
    HashMap<String, Integer> clickIdMap = new HashMap<>();
    Map<String, Integer> IssueToRow = new HashMap<>();
    ArrayList<String> allowedGeo = new ArrayList<String>();
    HashSet<String> hashSet = new HashSet<>();
    String[] mandate = { "d", "cc", "di", "subid", "enk" };
    String[] notValidDlParams = { "{deeplink}", "%7Bdeeplink%7D" };
    ArrayList<String> mandatoryParameters = new ArrayList<String>();
    ArrayList<String> notValidDL = new ArrayList<String>();

   
    for (int i = 0; i < mandate.length; i++) {
      mandatoryParameters.add(mandate[i]);
    }
    for (int i = 0; i < notValidDlParams.length; i++) {
      notValidDL.add(notValidDlParams[i]);
    }
    /**  HEADER WRITING IN CSV STARTS **/
    FileWriter outputfile_header;

    try {
      outputfile_header = new FileWriter(OUTPUT_FILE_NAME, true);
      CSVWriter writer_header = new CSVWriter(
        outputfile_header,
        '|',
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END
      );

      /** CSV HEADERS **/
      String[] header = {
        "Customer Key",
        "Customer Name",
        "TSID",
        "BrandID",
        "KingDomain",
        "URL",
        "Click Id",
        "SubID",
        "Country Code",
        "DeepLink",
        "Subid Present?",
        "Click Id Unique?",
        "Country Code Valid?",
        "DeepLink Valid?",
        "Invalid DeepLink Reason?",
        "Invalid params(Changed case)",
        "Repeating params",
      };
      writer_header.writeNext(header);
      writer_header.close();
    } catch (IOException e2) {
      e2.printStackTrace();
    }

    /**  HEADER WRITING IN CSV ENDS **/

    /** PROCESSING CSV DATA STARTS **/
    String line_cc = "";

    BufferedReader geo_cc = new BufferedReader(
      new FileReader(ALLOWED_GEO_LIST)
    );
    try {
      while ((line_cc = geo_cc.readLine()) != null) {
        String country_code = line_cc.split(",")[0];

        allowedGeo.add(country_code.replaceAll("^\"|\"$", ""));
      }
    } catch (IOException e3) {
      e3.printStackTrace();
    }

    String csv_id = "";
    try (
      BufferedReader csv_cc = new BufferedReader(new FileReader(CSV_TO_PROCESS_FINAL))
    ) {
      csv_cc.readLine();
      
      while ((csv_id = csv_cc.readLine()) != null) {
    	  System.out.println(csv_id);
        String[] values = csv_id.split("\\|");
        
        String customer_key = (String) values[0].replaceAll("^\"|\"$", "");
        String inputUrl = (String) values[7];
        Map<String, String> mapped = getQueryMap(
          inputUrl,
          checkDuplicateParams
        );

        String click_id = mapped.get("di");

        if (click_id != null) {
          if (clickIdMap.containsKey(customer_key + click_id)) {
            int exists = clickIdMap.get(customer_key + click_id);
            clickIdMap.put(customer_key + click_id, exists + 1);
          } else {
            clickIdMap.put(customer_key + click_id, 1);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    String line_csv = "";

    try (
      BufferedReader br_cc = new BufferedReader(new FileReader(CSV_TO_PROCESS_FINAL))
    ) {
      br_cc.readLine();
      while ((line_csv = br_cc.readLine()) != null) {
        String[] values = line_csv.split("\\|");

        String customerKey = values[0].replaceAll("^\"|\"$", "");
        String customerName = values[1].replace("\"", "");

        String TSID = values[2];
        String brandId = values[3];
        String kingDomain = values[5].replace("\"", "");
        String brandName = values[4];
        String kingDomainHost = values[6].replace("\"", "");
        String inputUrl = (String) values[7].replace("\"", "");
        Map<String, String> mapped = getQueryMap(
          inputUrl,
          checkDuplicateParams
        );

        //INVALID PARAMETER(CASE CHANGED) LOGIC

        for (var entry : mapped.entrySet()) {
          if (
            (!(entry.getKey().equals(entry.getKey().toLowerCase()))) &&
            mandatoryParameters.contains(entry.getKey().toLowerCase())
          ) {
            caseParameterCheck.put(entry.getKey().toLowerCase().trim(), "NO");
          } else {
            caseParameterCheck.remove(entry.getKey().toLowerCase());
          }
        }

        String subid = mapped.get("subid");
        String cc = mapped.get("cc");
        String d_link = mapped.get("d");
        String click_id = mapped.get("di");
        String d_link_condition = null;
        FileWriter outputfile = new FileWriter(OUTPUT_FILE_NAME, true);

        try (
          CSVWriter writer = new CSVWriter(
            outputfile,
            '|',
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END
          )
        ) {
          if (d_link != null && !(notValidDL.contains(d_link))) {
            if (
              (
                Pattern
                  .compile(
                    Pattern.quote(kingDomainHost),
                    Pattern.CASE_INSENSITIVE
                  )
                  .matcher(d_link)
                  .find()
              ) &&
              (checkEncode(d_link))
            ) {
              d_link_condition = "Yes";
            } else if (!d_link.contains(kingDomainHost)) {
              d_link_condition = "No - Kingdomain not found in deeplink ";
              kingDomainNotInDlFlag = true;
              kingDomainNotFoundCtr++;
            } else {
              d_link_condition = "No - Url is not encoded";
              deepLinkNotEncodedFlag = true;
              deepLinkNotEncodedCtr++;
            }
          } else {
            d_link_condition = "NA";
          }

          checkDuplicateParams.forEach(
            value -> {
              hashSet.add(value);
            }
          );

          if (subid == null) {

            subIdNullCtr++;
            subIdMissingFlag = true;
          }
          if (
            clickIdMap.get(customerKey + click_id) != null &&
            clickIdMap.get(customerKey + click_id) > 1
          ) {
            clickIdNotUniqueFlag = true;
            clickIdNonUniqueCtr++;
          }
          if (cc != null && !(allowedGeo.contains(cc.toUpperCase()))) {
            countryCodeInvalidFlag = true;
            countryCodeInvalidCtr++;
          }

          if (caseParameterCheck.size() > 0) {
            invalidCaseFlag = true;
            parameterMismatchCtr++;
          }
          if (hashSet.size() > 0) {
            repeatParamsFlag = true;
            repeatParamsCtr++;
          }

          /** CSV WRITING DATA VARIABLE **/
          String[] data = {
            customerKey,
            customerName,
            TSID,
            brandId,
            kingDomain,
            inputUrl,
            (click_id != null ? click_id : "NOT PRESENT"),
            (subid != null) ? (subid) : (""),
            (cc != null) ? (cc) : ("NOT PRESENT"),
            (d_link != null) ? (d_link.replaceAll("\"", "")) : ("NOT PRESENT"),
            (subid != null) ? ("Yes") : ("No"),
            (clickIdMap.get(customerKey + click_id) != null)
              ? (clickIdMap.get(customerKey + click_id) > 1) ? ("No") : ("Yes")
              : "NA",
            (cc != null)
              ? (allowedGeo.contains(cc.toUpperCase()) ? "Yes" : "No")
              : "NA",
            (d_link != null && !(notValidDL.contains(d_link)))
              ? ((d_link_condition.charAt(0) == 'Y') ? "Yes" : "No")
              : "NA",
            (
                d_link_condition != null &&
                d_link_condition.charAt(0) == 'N' &&
                d_link_condition.charAt(1) != 'A'
              )
              ? (d_link_condition.substring(5))
              : "",
            (caseParameterCheck.size() > 0)
              ? (
                caseParameterCheck
                  .keySet()
                  .toString()
                  .replace("[", "")
                  .replace("]", "")
              )
              : (""),
            (hashSet.size() > 0)
              ? (hashSet.toString().replace("[", "").replace("]", ""))
              : (""),
          };

          writer.writeNext(data);
        }
      }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      e.printStackTrace();
    }

    /** PROCESSING CSV DATA ENDS **/

    long endTime = System.currentTimeMillis();

    /** OUTPUT **/

    System.out.println("That took " + (endTime - startTime) + " milliseconds");
    System.out.println("Output file generated");
    System.out.println(OUTPUT_FILE_NAME);
    System.out.println("File Size - " + file.length() / 1024 + " KB");
    System.out.println("Date - " + ts);
    
    String flags = "";
    ArrayList<String> flag = new ArrayList<String>();
   
    final String xlsxString =
      "C:\\Users\\Arunkumar\\Desktop\\Fixed_Setup_Validation_" +
      dateInString +
      "_" +
      curr +
      ".xlsx";
    csvToXLSX(OUTPUT_FILE_NAME, xlsxString);
    if (subIdMissingFlag == true) {
      flag.add("Subid is missing!");
    }
    if (clickIdNotUniqueFlag == true) {
    
      flag.add("Click ID is not unique!");
    }
    if (countryCodeInvalidFlag == true) {
  
    	
      flag.add("Country code is invalid");
    }
    if (deepLinkNotEncodedFlag == true) {
      flag.add("Deeplink is not encoded");
    }
    if (kingDomainNotInDlFlag == true) {
     
      flag.add("Kingdomain not found in deeplink");
    }
    if (invalidCaseFlag == true) {
     
      flag.add("URL contains parameters with different cases");
    }
    if (repeatParamsFlag == true) {
   
      flag.add("Same parameters appeared multiple times in url");
    }

    IssueToRow.put("Subid is missing!", subIdNullCtr);
    IssueToRow.put("Click ID is not unique!", clickIdNonUniqueCtr);
    IssueToRow.put("Country code is invalid", countryCodeInvalidCtr);
    IssueToRow.put(
      "URL contains parameters with different cases",
      parameterMismatchCtr
    );
    IssueToRow.put("Deeplink is not encoded", deepLinkNotEncodedCtr);
    IssueToRow.put("Kingdomain not found in deeplink", kingDomainNotFoundCtr);
    IssueToRow.put(
      "Same parameters appeared multiple times in url",
      repeatParamsCtr
    );
    Map<String, Integer> SortedVal = sortByValue(IssueToRow);
    
    
   

    if (flag.size() > 0) {
      sendSmtpMail(flag, xlsxString,CSV_TO_PROCESS_FINAL, SortedVal,dateInString);
    	
//    	System.out.println("OK");
    }
  }
}

