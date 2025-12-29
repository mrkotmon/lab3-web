package dataBaseUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ResultEntity implements Serializable {

    private Long id;
    private double x;
    private double y;
    private double r;
    private boolean result;       // попал / не попал
    private LocalDateTime time;   // время попадания

    public ResultEntity() {
    }

    public ResultEntity(double x, double y, double r, boolean result, LocalDateTime time) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
