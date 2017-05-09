package backendTesting.model;

import java.math.BigDecimal;

/**
 * Created by elinkin on 07/05/2017.
 */
public class Wind {

    private BigDecimal speed;
    private BigDecimal deg;

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getDeg() {
        return deg;
    }

    public void setDeg(BigDecimal deg) {
        this.deg = deg;
    }
}
