import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
            accountList.forEach( account -> CreateAccount( (JSONObject) account ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void CreateAccount (JSONObject account){
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

    public static void ConvertJSONToHTML(){

    }

    public static void ConvertHTMLToPDF(){

    }
}
