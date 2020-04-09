public class StockTrade {
    private String type;
    private String stockSymbol;
    private Long countShares;
    private Double pricePerShare;

    public StockTrade(String type, String stockSymbol, Long countShares, Double pricePerShare) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.countShares = countShares;
        this.pricePerShare = pricePerShare;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public Long getCountShares() {
        return countShares;
    }

    public void setCountShares(Long countShares) {
        this.countShares = countShares;
    }

    public Double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(Double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    @Override
    public String toString() {
        return "StockTrades{" + '\n' +
                "type='" + type + '\'' + '\n' +
                "stockSymbol='" + stockSymbol + '\'' + '\n' +
                "countShares=" + countShares + '\n' +
                "pricePerShare='" + pricePerShare + '\'' + '\n' +
                '}';
    }
}
