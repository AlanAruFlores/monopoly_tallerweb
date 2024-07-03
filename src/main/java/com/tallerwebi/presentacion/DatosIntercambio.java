package com.tallerwebi.presentacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DatosIntercambio {
    public Long emisorId;
    public Long receptorId;

    public Double saldoEmisor;
    public Double saldoReceptor;

    public Long idPropiedadEmisorUno;
    public Long idPropiedadEmisorDos;

    public Long idPropiedadReceptorUno;
    public Long idPropiedadReceptorDos;
}
