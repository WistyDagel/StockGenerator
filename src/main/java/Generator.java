import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Generator {

    private static ArrayList<StockTrade> stockTradeList;

    public static void main(String[] args)
    {
        //Read the JSON Data
        ReadJSONData();

        //Convert the JSON to HTML
//        ConvertJSONToHTML();

        //Convert the HTML into PDF
//        ConvertHTMLToPDF();
    }

    public static void ReadJSONData(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("stock_transations.by.account.holder.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray accountList = (JSONArray) obj;

            //Iterate over employee array
            accountList.forEach( account -> {
                try {
                    CreateAccount( (JSONObject) account );
                } catch (IOException e) {
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

    public static void CreateAccount (JSONObject account) throws IOException {
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

        Account account1 = new Account(accountNumber, ssn, firstName, lastName, email, phone, actualBalance);
        account1.setStockTradeList(stockTradeList);

        //Test
        System.out.println(account1.toString());

        //Test
//        account1.getStockTradeList().forEach(stockTrade -> {
//            System.out.println(stockTrade.toString());
//        });

        ConvertJSONToHTML(account1);
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

    public static void ConvertJSONToHTML(Account account) throws IOException {
        int x = 0;
        OutputStream outputStream = new FileOutputStream("./HTMLFiles/" + x + ".html");
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

        outputStreamWriter.write("</body></html>");

        outputStreamWriter.close();

        x++;
    }

    public static void ConvertHTMLToPDF(){

    }
}
