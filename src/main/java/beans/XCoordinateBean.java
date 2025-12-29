package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "xCoordinateBean")
@SessionScoped
public class XCoordinateBean implements Serializable {

    private String xRadio;
    private Double xCanvas;

    public String getxRadio() { return xRadio; }
    public void setxRadio(String xRadio) { this.xRadio = xRadio; }

    public Double getxCanvas() { return xCanvas; }
    public void setxCanvas(Double xCanvas) { this.xCanvas = xCanvas; }
}
