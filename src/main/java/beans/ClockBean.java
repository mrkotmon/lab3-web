package beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;

@ManagedBean(name="clockBean")
@ApplicationScoped
public class ClockBean implements Serializable {

    private Date now = new Date();

    public void refresh() {
        now = new Date(); 
    }

    public Date getNow() {
        return now;
    }
}
