package interfaces;

import java.util.Date;

public interface Comestibles {
    public void setFechaVencimiento(Date fechaVencimiento);
    public Date getFechaVencimiento();
    public void setCalorias(Integer calorias);
    public Integer getCalorias();
}