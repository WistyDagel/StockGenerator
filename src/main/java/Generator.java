import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Generator {

    private static ArrayList<StockTrade> stockTradeList;
    private static Account account1;

    public static void main(String[] args) throws Exception {
        //Read the JSON Data
        ReadJSONData();

        //Convert the JSON to HTML
//        ConvertJSONToHTML();

        //Convert the HTML into PDF
        ConvertHTMLToPDF();
    }

    public static void ReadJSONData(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("stock_transations.by.account.holder.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray accountList = (JSONArray) obj;

            accountList.forEach( account -> {
                try {
                    CreateAccount( (JSONObject) account );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void CreateAccount (JSONObject account) throws Exception {
        Long accountNumber = (Long) account.get("account_number");
        String ssn =  (String) account.get("ssn");
        String firstName = (String) account.get("first_name");
        String lastName = (String) account.get("last_name");
        String email = (String) account.get("email");
        String phone = (String) account.get("phone");
        String beginningBalance = (String) account.get("beginning_balance");

        Double actualBalance = Double.parseDouble(beginningBalance.replaceAll("[$]", ""));

        JSONArray stockTrades = (JSONArray) account.get("stock_trades");

        stockTradeList = new ArrayList<StockTrade>();

        stockTrades.forEach(stockTrade -> stockTradeList.add(HandleStockTrades( (JSONObject) stockTrade)));

        account1 = new Account(accountNumber, ssn, firstName, lastName, email, phone, actualBalance);
        account1.setStockTradeList(stockTradeList);

        //Test
//        System.out.println(account1.toString());

        //Test
//        account1.getStockTradeList().forEach(stockTrade -> {
//            System.out.println(stockTrade.toString());
//        });

        HandleCashAndStockHoldings(account1);
        ConvertJSONToHTML(account1);
    }

    public static void HandleCashAndStockHoldings(Account account) {
        account.getStockTradeList().forEach(stockTrade -> {
            //BUY
            if (stockTrade.getType().equals("Buy")){
                //Removes the amount of shares purchased from the current balance
                account.setCash_amount(( account.getCash_amount() - (stockTrade.getPricePerShare() * stockTrade.getCountShares())));
                //Adds the amount of stock holdings to the account
                account.setStock_holdings((double) account.getStock_holdings() + stockTrade.getCountShares());
            }
            //SELL
            else {
                //Add the amount of shares sold from the current balance
                account.setCash_amount(account.getCash_amount() + (stockTrade.getPricePerShare() * stockTrade.getCountShares()));
                //Removes the amount of stock holdings to the account
                account.setStock_holdings((double) account.getStock_holdings() - stockTrade.getCountShares());
            }
        });
    }

    public static StockTrade HandleStockTrades(JSONObject stock) {
        String type = (String) stock.get("type");
        String stockSymbol = (String) stock.get("stock_symbol");
        Long countShare = (Long) stock.get("count_shares");
        String pricePerShare = (String) stock.get("price_per_share");

        Double perShare = Double.parseDouble(pricePerShare.replaceAll("[$]", ""));

        StockTrade stockTrade = new StockTrade(type, stockSymbol, countShare, perShare);

        return stockTrade;
    }

    public static void ConvertJSONToHTML(Account account) throws Exception {
        OutputStream outputStream = new FileOutputStream("./HTMLFiles/" + account.getAccount_number() + ".html");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        outputStreamWriter.write("<!DOCTYPE html>");
        outputStreamWriter.write("<html><body>");

        outputStreamWriter.write(String.format("%s", account.toString()));

        outputStreamWriter.write("<table>");
        outputStreamWriter.write("<tr>" +
                "<th>Type</th>" +
                "<th>Stock Symbol</th>" +
                "<th>Count of Shares</th>" +
                "<th>Price Per Share</th>" +
                "</tr>");
        account.getStockTradeList().forEach(stockTrade -> {
            try {
                outputStreamWriter.write(String.format("%s", stockTrade.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        outputStreamWriter.write("</table>");

        outputStreamWriter.write("<h3> Current Cash Balance: " + account.getCash_amount() + "</h3>");
        outputStreamWriter.write("<h3> Current Stock Holdings: " + account.getStock_holdings() + "</h3>");

        outputStreamWriter.write("</body></html>");

        outputStreamWriter.close();
    }

    public static void ConvertHTMLToPDF() throws Exception {
        File dir = new File("./HTMLFiles/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String filename = child.toString().replaceAll(".\\\\HTMLFiles\\\\", "");
                filename = filename.replaceAll(".html", "");
                try (OutputStream os = new FileOutputStream("./PDF/" + filename + ".pdf")) {
                    PdfRendererBuilder builder = new PdfRendererBuilder();
                    builder.withUri("file:///C:/Users/Chris/Documents/Homework%20-%20Neumont/Quarter%207/Open%20Source%20Platforms%20Development/StockGenerator/HTMLFiles/" + filename + ".html");
                    builder.toStream(os);
                    builder.run();
                }

            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
    }
}
