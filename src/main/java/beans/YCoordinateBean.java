package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "yCoordinateBean")
@SessionScoped
public class YCoordinateBean implements Serializable {

    private Double yInput;
    private Double yCanvas;

    public Double getYInput() { return yInput; }
    public void setYInput(Double yInput) { this.yInput = yInput; }

    public Double getYCanvas() { return yCanvas; }
    public void setYCanvas(Double yCanvas) { this.yCanvas = yCanvas; }
}
