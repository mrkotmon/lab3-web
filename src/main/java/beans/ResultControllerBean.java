package beans;

import dataBaseUtils.ResultDAO;
import dataBaseUtils.ResultEntity;
import utils.HitChecker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(name = "ControllerBean")
@ApplicationScoped
public class ResultControllerBean implements Serializable {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private ResultDAO resultDAO = new ResultDAO();
    private List<ResultEntity> results = new ArrayList<>();


    @PostConstruct
    public void init() {
        results = resultDAO.getAll();
        if (results == null) {
            results = new ArrayList<>();
        }
        logger.info("ResultControllerBean инициализирован, загружено " + results.size() + " строк");
    }

    public List<ResultEntity> getResults() {
        return results;
    }

    public void addResult(Double x, Double y, Double r) {
        logger.info("Поступили значения: x=" + x + ", y=" + y + ", r=" + r);

        if (!validate(x, y, r)) {
            logger.warning("Валидация не пройдена");
            return;
        }

        x = truncate(x, 3);
        y = truncate(y, 3);

        boolean hit = HitChecker.hit(x, y, r);

        ResultEntity entity = new ResultEntity(
                x, y, r, hit, LocalDateTime.now()
        );

        resultDAO.save(entity);
        results.add(entity);

        logger.info(String.format(
                "Добавлен новый результат: X=%.3f, Y=%.3f, R=%.3f, hit=%s",
                x, y, r, hit
        ));
    }

    public void clearResults() {
        resultDAO.clear();
        results.clear();
    }

    private boolean validate(Double x, Double y, Double r) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        boolean ok = true;

        if (x == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "X не задан", null));
            ok = false;
        } else if (x < -4 || x > 4) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "X должен быть в диапазоне [-4; 4]", null));
            ok = false;
        }

        if (y == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Y не задан", null));
            ok = false;
        } else if (y < -5 || y > 3) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Y должен быть в диапазоне [-5; 3]", null));
            ok = false;
        }

        if (r == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "R не задан", null));
            ok = false;
        } else if (r <= 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "R должен быть положительным", null));
            ok = false;
        }

        return ok;
    }

    private double truncate(double value, int digits) {
        double factor = Math.pow(10, digits);
        double res = Math.round(value * factor) / factor;

        if (res == 0.0d) res = 0.0d;

        return res;
    }

}
