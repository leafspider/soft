package soft.rules;

import java.util.ArrayList;

public class AssetDelta {

    private ArrayList<String[]> prices = null;
    public ArrayList<String[]> getPrices() { return prices; }
    public void setPrices(ArrayList<String[]> prices) { this.prices = prices; }

    public AssetDelta( ArrayList<String[]> prices ) {
        this.prices = prices;
        doDeltas();
    }

    private Double pricePC = 0.0D;
    public Double getPricePC() { return pricePC; }
    public void setPricePC(Double pricePC) { this.pricePC = pricePC; }
    public String getFormattedPricePC() { return String.format( "%,.2f", pricePC ); }

    private Double volumePC = 0.0D;
    public Double getVolumePC() { return volumePC; }
    public void setVolumePC(Double volumePC) { this.volumePC = volumePC; }
    public String getFormattedVolumePC() { return String.format( "%,.2f", volumePC ); }

    public void doDeltas() {

        int j = 0;

        String[] cols = (String[]) prices.get(j);
        String[] prevCols = (String[]) prices.get(j + 1);   // Rows are date descending

        Double close = Double.valueOf(cols[4]);
        Double prevClose = Double.valueOf(prevCols[4]);
        Double diff = close - prevClose;
        pricePC = 100 * (diff / prevClose);
        cols[5] = "" + diff.floatValue();

        Double vol = Double.valueOf(cols[6]);
        Double prevVol = Double.valueOf(prevCols[6]);
        Double volDiff = vol - prevVol;
        if (Math.abs(volDiff) > 0.0) {
            if (prevVol == 0.0) {
                volumePC = 100.0;
            } else {
                volumePC = 100 * (volDiff / prevVol);
            }
        }
    }

}
