import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {

    @Test
    void readJSONData() {
        //arrange
        //Dumby JSON Data

        //act

        //assert

    }

    @Test
    void createAccount() {
        //arrange

        //Dumby Account object
        Account account = new Account((long) 123, "123-45-678", "John", "Doe", "johndoe@email.com", "123-456-7890", 12345.67);

        //act

        //assert
    }

    @Test
    void handleStockTrades() {
        //arrange

        //Dumby StockTrade object
        StockTrade stockTrade = new StockTrade("BUY", "TEST", (long) 1111, 000.00);

        //act

        //assert
    }

    @Test
    void convertJSONToHTML() throws Exception {
        //arrange

        //Dumby Account object
        Account account = new Account((long) 123, "123-45-678", "John", "Doe", "johndoe@email.com", "123-456-7890", 12345.67);

        //act
        //Call method
        Generator.ConvertJSONToHTML(account);

        //assert
        //check if the file exists
        //It should appear in the ./HTMLFiles folder
    }

    @Test
    void convertHTMLToPDF() {
        //arrange

        //Dumby HTML object

        //act

        //assert
    }
}