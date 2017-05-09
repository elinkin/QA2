package backendTesting.model;

import java.math.BigDecimal;

/**
 * Created by elinkin on 07/05/2017.
 */
public class Coord {

    private BigDecimal lon;
    private BigDecimal lat;

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
}
