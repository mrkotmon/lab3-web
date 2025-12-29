package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "rCoordinateBean")
@SessionScoped
public class RCoordinateBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double r;

    public RCoordinateBean() {
        this.r = 1.0;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }
}
